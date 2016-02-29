package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import com.common.tool.util.PageBean;
import com.common.tool.util.StringUtil;
import com.entity.Campus;
import com.service.CampusService;
import com.util.ProjectConfig;

/**
 * Campus控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("campus")
public class CampusController {

	@Resource
	private CampusService campusService;

	/**
	 * 按分页效果显示数据内容
	 * 
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("view_campus")
	public String view_campus(Page page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		PageBean pg = campusService.getCampusPageByMap(page, map);
		request.setAttribute("pg", pg);
		// request.setAttribute("campusList", campusService.getCampusList());
		request.setAttribute("nav_tag", "CONFIG");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_campus";
	}

	/**
	 * 创建一个学习中心对象
	 * 
	 * @param campus
	 * @param request
	 * @return
	 */
	@RequestMapping("/addCampus")
	public String addCampus(Campus campus, HttpServletRequest request) {
		campusService.createCampus(campus);
		return "redirect:/campus/view_campus";
	}

	/**
	 * 删除一个学习中心对象
	 * 
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteCampus")
	public String deleteCampus(Integer id, HttpServletResponse response) {
		campusService.deleteCampusById(id);
		return "redirect:/campus/view_campus";
	}

	/**
	 * 更新学习中心对象
	 * 
	 * @param campus
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateCampus")
	public String updateCampus(Campus campus, HttpServletRequest request) {
		campusService.updateCampus(campus);
		return "redirect:/campus/view_campus";
	}

	/**
	 * 上传Excel格式学习中心数据
	 * 
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 */
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
				campusService.importExcel(targetFile);

			}
		}
		return "redirect:/campus/view_campus";
	}

	/**
	 * 批量删除Excel格式学习中心数据
	 * 
	 * @param ids
	 * @param response
	 * @return
	 */
	@RequestMapping("/deleteAllCampus")
	public String deleteAllCampus(String ids, HttpServletResponse response) {
		campusService.deleteAll(ids);
		return "redirect:/campus/view_campus";
	}

	/**
	 * 将学习中心数据导出到Excel中
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("学习中心记录").getBytes(), "ISO8859-1");
			String datetime = DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName + datetime+ ".xlsx");// 组装附件名称和格式

			campusService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
