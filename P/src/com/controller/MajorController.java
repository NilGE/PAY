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
import com.entity.Major;
import com.service.MajorService;
import com.util.ProjectConfig;


/**
 * Major控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("major")
public class MajorController {

	@Resource
	private MajorService majorService;
	
	

	@RequestMapping("view_major")
	public String view_major(Page page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		PageBean pg = majorService.getMajorPageByMap(page, map);
		request.setAttribute("pg", pg);
		//request.setAttribute("majorList", majorService.getMajorList());
		request.setAttribute("nav_tag", "CONFIG");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_major";
	}

	@RequestMapping("/addMajor")
	public String addMajor(Major major, HttpServletRequest request) {
		majorService.createMajor(major);
		return "redirect:/major/view_major";
	}

	// @RequestMapping("/deleteMajor")
	@RequestMapping(value = "/deleteMajor")
	public String deleteMajor(Integer id, HttpServletResponse response) {
		majorService.deleteMajorById(id);
		return "redirect:/major/view_major";
	}

	@RequestMapping("/updateMajor")
	public String updateMajor(Major major, HttpServletRequest request) {
		majorService.updateMajor(major);
		return "redirect:/major/view_major";
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
				majorService.importExcel(targetFile);

			}
		}
		return "redirect:/major/view_major";
	}
	
	
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部专业记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName + DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") +".xlsx");// 组装附件名称和格式

			majorService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/deleteAllMajor")
	public String deleteAllMajor(String ids, HttpServletResponse response) {
		majorService.deleteAll(ids);
		return "redirect:/major/view_major";
	}
	
	
	
}
