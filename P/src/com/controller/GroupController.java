package com.controller;

import java.io.File;
import java.io.IOException;

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
import com.entity.Group;
import com.service.GroupService;
import com.util.ProjectConfig;


/**
 * Group控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("group")
public class GroupController {

	@Resource
	private GroupService groupService;
	
	

	@RequestMapping("view_group")
	public String view_group(HttpServletRequest request) {
		request.setAttribute("groupList", groupService.getGroupList());
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_group";
	}

	@RequestMapping("/addGroup")
	public String addGroup(Group group, HttpServletRequest request) {
		groupService.createGroup(group);
		return "redirect:/group/view_group";
	}

	// @RequestMapping("/deleteGroup")
	@RequestMapping(value = "/deleteGroup")
	public String deleteGroup(Integer id, HttpServletResponse response) {
		groupService.deleteGroupById(id);
		return "redirect:/group/view_group";
	}

	@RequestMapping("/updateGroup")
	public String updateGroup(Group group, HttpServletRequest request) {
		groupService.updateGroup(group);
		return "redirect:/group/view_group";
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
				groupService.importExcel(targetFile);

			}
		}
		return "redirect:/group/view_group";
	}
	
	
	@RequestMapping("/deleteAllGroup")
	public String deleteAllGroup(String ids, HttpServletResponse response) {
		groupService.deleteAll(ids);
		return "redirect:/group/view_group";
	}
	/**
	 * Export group data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("group记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName + DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") +".xlsx");// 组装附件名称和格式

			groupService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
