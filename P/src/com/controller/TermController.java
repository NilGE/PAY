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
import org.springframework.web.multipart.MultipartFile;

import com.common.tool.util.DateUtil;
import com.common.tool.util.Page;
import com.common.tool.util.PageUtil;
import com.entity.Term;
import com.service.StandardService;
import com.service.TermService;
import com.util.ProjectConfig;


/**
 * Term控制器
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Controller
@RequestMapping("term")
public class TermController {

	@Resource
	private TermService termService;
	
	@Resource
    private StandardService standardService;


	
	/**
	 * 按分页效果显示term数据内容
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("view_term")
	public String view_term( PageUtil pu, HttpServletRequest request) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Term> list = termService.getTermListByMap(map);
		System.out.println(pu.getSize());
		pu.init(list, pu.getSize());
		pu.setAction("../term/view_term");
		request.setAttribute("pu", pu);
		
/*		PageBean pageBean = termService.getTermPageByMap(page, map);
		request.setAttribute("pageBean", pageBean);*/
		request.setAttribute("nav_tag", "CONFIG");
		request.setAttribute("standardList", standardService.getStandardList());
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_term";
	}
	
	/**
	 * 设置动态分成比例
	 * @param request
	 * @return
	 */
	@RequestMapping("view_dynamic")
	public String view_dynamic(Page page, PageUtil pu, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Term> list = termService.getTermListByMap(map);
		pu.init(list, pu.getSize());
		pu.setAction("../term/view_dynamic");
		request.setAttribute("pu", pu);
		
		/*PageBean pageBean = termService.getTermPageByMap(page, map);
		request.setAttribute("pageBean", pageBean);*/
		request.setAttribute("standardList", standardService.getStandardList());
		request.setAttribute("nav_tag", "PAYMENT");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_dynamic";
	}


	@RequestMapping("/addTerm")
	public String addTerm(Term term, HttpServletRequest request) {
		termService.createTerm(term);
		return "redirect:/term/view_term";
	}

	@RequestMapping("/deleteTerm")
	public String deleteTerm(Integer id, HttpServletResponse response) {
		termService.deleteTermById(id);
		return "redirect:/term/view_term";
	}

	@RequestMapping("/updateTerm")
	public String updateTerm(Term term, HttpServletRequest request) {
		termService.updateTerm(term);
		return "redirect:/term/view_term";
	}
	
	@RequestMapping("/deleteDynamic")
	public String deleteDynamic(Integer id, HttpServletResponse response) {
		termService.deleteTermById(id);
		return "redirect:/term/view_dynamic";
	}

	@RequestMapping("/updateDynamic")
	public String updateDynamic(Term term, HttpServletRequest request) {
		termService.updateTerm(term);
		return "redirect:/term/view_dynamic";
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
				termService.importExcel(targetFile);

			}
		}
		return "redirect:/term/view_term";
	}
	
	
	@RequestMapping("/deleteAllTerm")
	public String deleteAllTerm(String ids, HttpServletResponse response) {
		termService.deleteAll(ids);
		return "redirect:/term/view_term";
	}
	/**
	 * Export term data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部批次记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			termService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
