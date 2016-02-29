package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.common.tool.util.DateUtil;
import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.common.tool.util.PageUtil;
import com.common.tool.util.StringUtil;
import com.entity.Campus;
import com.entity.Record;
import com.entity.User;
import com.service.CampusService;
import com.service.RecordService;
import com.service.UserService;
import com.service.entiy.CampusPay;
import com.util.ProjectConfig;

/**
 * Record控制器
 * 
 * @author admin
 * @version 2015-04-07 16:54:35
 */
@Controller
@RequestMapping("record")
public class RecordController {

	@Resource
	private RecordService recordService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CampusService campusService;

	@RequestMapping("view_record")
	public String view_record(Integer campus_id, Page page, HttpServletRequest request) {
		//System.out.println(page.getCurrentPage());
		Campus campus = null;
		Map<String, Object> map = new HashMap<String, Object>();
		String groupCode = (String) request.getSession().getAttribute("groupCode");
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		User admin = userService.getUserById(user_id);
		if ("PAY".equals(groupCode)) {
			campus_id = admin.getCampus().getId();
			campus = campusService.getCampusById(campus_id);
			map.put("campusCode", campus.getCampusCode());
		}
		
		request.setAttribute("nav_tag", "RECORD");
		if (!StringUtil.checkNull(campus_id)) {
			
			campus = campusService.getCampusById(campus_id);
			map.put("campusCode", campus.getCampusCode());
		}
		
		map.put("disabled", false);
		PageBean pageBean = recordService.getRecordPageByMap(page, map);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("nav_tag", "RECORD");
		request.setAttribute("campusList", campusService.getCampusList());
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_record";
	}
	
	/**
	 * 删除缓存计算返款比例的数据
	 * @param username
	 * @param request
	 * @return
	 */
	@RequestMapping("/clearCache")
	@ResponseBody
	public Object checkReg(String username, HttpServletRequest request) {
		request.getSession().removeAttribute("LIST");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "清除缓存数据成功！");
		return map;

	}
	
	/**
	 * 计算数据，并缓存到Session
	 * @param pu
	 * @param campus_id
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("analyseRecord")
	public String analyseRecord(PageUtil pu, Integer campus_id, HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		request.setAttribute("nav_tag", "RECORD");
		
		if (!StringUtil.checkNull(request.getSession().getAttribute("LIST"))) {
			List<CampusPay> campusPays = ((List<CampusPay>) request.getSession().getAttribute("LIST"));
			pu.init(campusPays, pu.getSize());
			pu.setAction("../record/analyseRecord");
			request.setAttribute("pu", pu);
			request.setAttribute("campusList", campusService.getCampusList());
			return "admin/analyse_record";
		}
		//System.out.println("List");
		String groupCode = (String) request.getSession().getAttribute("groupCode");
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		User admin = userService.getUserById(user_id);
		if ("PAY".equals(groupCode)) {
			campus_id = admin.getCampus().getId();
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtil.checkNull(campus_id)) {
			map.put("campus_id", campus_id);
		}
		List<CampusPay> campusPays = recordService.calculate(map);
		
		pu.init(campusPays, pu.getSize());
		pu.setAction("../record/analyseRecord");
		request.getSession().setAttribute("LIST", campusPays);
		request.setAttribute("pu", pu);
		request.setAttribute("campusList", campusService.getCampusList());
		
		return "admin/analyse_record";
	}
	
	
	

	@RequestMapping("/addRecord")
	public String addRecord(Record record, HttpServletRequest request) {
		recordService.createRecord(record);
		//更新缓存
		request.getSession().removeAttribute("PU");
		return "redirect:/record/view_record";
	}

	// @RequestMapping("/deleteRecord")
	@RequestMapping(value = "/deleteRecord")
	public String deleteRecord(Integer id, HttpServletRequest request) {
		recordService.deleteRecordById(id);
		//更新缓存
		request.getSession().removeAttribute("PU");
		return "redirect:/record/view_record";
	}

	@RequestMapping("/updateRecord")
	public String updateRecord(Record record, HttpServletRequest request) {
		recordService.updateRecord(record);
		return "redirect:/record/view_record";
	}

	@RequestMapping("/data_in")
	public String data_in(HttpServletRequest request) {
		request.setAttribute("nav_tag", "RECORD");
		return "admin/data_in";
	}

	@RequestMapping("/data_out")
	public String data_out(HttpServletRequest request) {
		request.setAttribute("nav_tag", "RECORD");
		return "admin/data_out";
	}

	@RequestMapping("/importExcel")
	public String importExcel(@RequestParam MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				System.out.println("文件未上传");
			} else {
				System.out.println("文件长度: " + myfile.getSize());
				System.out.println("文件类型: " + myfile.getContentType());
				System.out.println("文件名称: " + myfile.getName());
				System.out.println("文件原名: " + myfile.getOriginalFilename());
				System.out.println("========================================");
				// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
				String realPath = request.getSession().getServletContext()
						.getRealPath("/upload");
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				File targetFile = new File(realPath,
						myfile.getOriginalFilename());
				FileUtils.copyInputStreamToFile(myfile.getInputStream(),
						targetFile);
				recordService.importExcel(targetFile);

				//更新缓存
				request.getSession().removeAttribute("PU");
			}
		}
		return "redirect:/record/view_record";
	}

	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			Integer user_id = (Integer) request.getSession().getAttribute("user_id");
			User user = userService.getUserById(user_id);
			
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部报表记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			recordService.exportExcel(user, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/exportBackMoney")
	public String exportBackMoney(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			Integer user_id = (Integer) request.getSession().getAttribute("user_id");
			User u = userService.getUserById(user_id);
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部返款记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			recordService.exportBackMoney(u, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/exportFile")
	public String exportFile(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			Integer user_id = (Integer) request.getSession().getAttribute("user_id");
			User u = userService.getUserById(user_id);
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("导出报表").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			recordService.exportFile(u, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/deleteAllRecord")
	public String deleteAllRecord(String ids, HttpServletRequest request) {
		recordService.deleteAll(ids);
		//更新缓存
		request.getSession().removeAttribute("PU");
		return "redirect:/record/view_record";
	}

}
