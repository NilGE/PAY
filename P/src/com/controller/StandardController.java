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
import com.common.tool.util.PageBean;
import com.common.tool.util.PageUtil;
import com.entity.Standard;
import com.service.StandardService;
import com.util.ProjectConfig;


/**
 * Standard控制器
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Controller
@RequestMapping("standard")
public class StandardController {

	@Resource
	private StandardService standardService;
	
	
	
	/**
	 * 按分页效果显示standard数据内容
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("view_standard")
	public String view_standard(Page page, PageUtil pu, HttpServletRequest request) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Standard> list = standardService.getStandardListByMap(map);
		pu.init(list, pu.getSize());
		pu.setAction("../standard/view_standard");
		request.setAttribute("pu", pu);
		
		PageBean pageBean = standardService.getStandardPageByMap(page, map);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		request.setAttribute("nav_tag", "PAYMENT");
		return "admin/view_standard";
	}


	@RequestMapping("/addStandard")
	public String addStandard(Standard standard, HttpServletRequest request) {
		standardService.createStandard(standard);
		return "redirect:/standard/view_standard";
	}

	@RequestMapping(value = "/deleteStandard")
	public String deleteStandard(Integer id, HttpServletResponse response) {
		standardService.deleteStandardById(id);
		return "redirect:/standard/standard";
	}

	@RequestMapping("/updateStandard")
	public String updateStandard(Standard standard, HttpServletRequest request) {
		standardService.updateStandard(standard);
		return "redirect:/standard/standard";
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
				standardService.importExcel(targetFile);

			}
		}
		return "redirect:/standard/view_standard";
	}
	
	
	@RequestMapping("/deleteAllStandard")
	public String deleteAllStandard(String ids, HttpServletResponse response) {
		standardService.deleteAll(ids);
		return "redirect:/standard/view_standard";
	}
	/**
	 * Export standard data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部结算标准记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName+DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss")  + ".xlsx");// 组装附件名称和格式

			standardService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
