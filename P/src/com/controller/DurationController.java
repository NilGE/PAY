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
import com.common.tool.util.PageUtil;
import com.entity.Duration;
import com.service.DurationService;
import com.service.TermService;
import com.util.ProjectConfig;


/**
 * Duration控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("duration")
public class DurationController {

	@Resource
	private DurationService durationService;
	
	@Resource
    private TermService termService;



	@RequestMapping("view_duration")
	public String view_duration(PageUtil pu, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Duration> list = durationService.getDurationListByMap(map);
		pu.init(list, pu.getSize());
		pu.setAction("../duration/view_duration");
		request.setAttribute("pu", pu);
		request.setAttribute("termList", termService.getTermList());
		request.setAttribute("nav_tag", "PAYMENT");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_duration";
	}
	
	@RequestMapping("duration_content")
	public String duration_content(int duration_id, HttpServletRequest request) {
		request.setAttribute("duration", durationService.getDurationById(duration_id));
		request.setAttribute("termList", termService.getTermList());
		request.setAttribute("nav_tag", "PAYMENT");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/duration_content";
	}

	@RequestMapping("/addDuration")
	public String addDuration(Duration duration, HttpServletRequest request) {
		durationService.createDuration(duration);
		return "redirect:/duration/view_duration";
	}

	// @RequestMapping("/deleteDuration")
	@RequestMapping(value = "/deleteDuration")
	public String deleteDuration(Integer id, HttpServletResponse response) {
		durationService.deleteDurationById(id);
		return "redirect:/duration/view_duration";
	}

	@RequestMapping("/updateDuration")
	public String updateDuration(Duration duration, HttpServletRequest request) {
		durationService.updateDuration(duration);
		return "redirect:/duration/view_duration";
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
				durationService.importExcel(targetFile);

			}
		}
		return "redirect:/duration/view_duration";
	}
	
	
	@RequestMapping("/deleteAllDuration")
	public String deleteAllDuration(String ids, HttpServletResponse response) {
		durationService.deleteAll(ids);
		return "redirect:/duration/view_duration";
	}
	/**
	 * Export duration data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部缴费周期记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			durationService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
