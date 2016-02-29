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
import com.entity.Level;
import com.service.LevelService;
import com.util.ProjectConfig;


/**
 * Level控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("level")
public class LevelController {

	@Resource
	private LevelService levelService;
	
	

	@RequestMapping("view_level")
	public String view_level(PageUtil pu, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Level> list = levelService.getLevelListByMap(map);
		pu.init(list, pu.getSize());
		pu.setAction("../level/view_level");
		request.setAttribute("pu", pu);
		request.setAttribute("nav_tag", "CONFIG");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_level";
	}

	@RequestMapping("/addLevel")
	public String addLevel(Level level, HttpServletRequest request) {
		levelService.createLevel(level);
		return "redirect:/level/view_level";
	}

	// @RequestMapping("/deleteLevel")
	@RequestMapping(value = "/deleteLevel")
	public String deleteLevel(Integer id, HttpServletResponse response) {
		levelService.deleteLevelById(id);
		return "redirect:/level/view_level";
	}

	@RequestMapping("/updateLevel")
	public String updateLevel(Level level, HttpServletRequest request) {
		levelService.updateLevel(level);
		return "redirect:/level/view_level";
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
				levelService.importExcel(targetFile);

			}
		}
		return "redirect:/level/view_level";
	}
	
	
	@RequestMapping("/deleteAllLevel")
	public String deleteAllLevel(String ids, HttpServletResponse response) {
		levelService.deleteAll(ids);
		return "redirect:/level/view_level";
	}
	/**
	 * Export level data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部层次记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			levelService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
