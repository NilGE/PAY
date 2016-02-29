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
import com.entity.Percent;
import com.service.PercentService;
import com.util.ProjectConfig;


/**
 * Percent控制器
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Controller
@RequestMapping("percent")
public class PercentController {

	@Resource
	private PercentService percentService;
	
	
	
	/**
	 * 按分页效果显示percent数据内容
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("view_percent")
	public String view_percent(Page page, PageUtil pu, HttpServletRequest request) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Percent> list = percentService.getPercentListByMap(map);
		pu.init(list, pu.getSize());
		pu.setAction("../percent/view_percent");
		request.setAttribute("pu", pu);
		
		PageBean pageBean = percentService.getPercentPageByMap(page, map);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		request.setAttribute("nav_tag", "PAYMENT");
		return "admin/view_percent";
	}


	@RequestMapping("/addPercent")
	public String addPercent(Percent percent, HttpServletRequest request) {
		percentService.createPercent(percent);
		return "redirect:/percent/view_percent";
	}

	@RequestMapping(value = "/deletePercent")
	public String deletePercent(Integer id, HttpServletResponse response) {
		percentService.deletePercentById(id);
		return "redirect:/percent/view_percent";
	}

	@RequestMapping("/updatePercent")
	public String updatePercent(Percent percent, HttpServletRequest request) {
		percentService.updatePercent(percent);
		return "redirect:/percent/view_percent";
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
				percentService.importExcel(targetFile);

			}
		}
		return "redirect:/percent/view_percent";
	}
	
	
	@RequestMapping("/deleteAllPercent")
	public String deleteAllPercent(String ids, HttpServletResponse response) {
		percentService.deleteAll(ids);
		return "redirect:/percent/view_percent";
	}
	/**
	 * Export percent data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部弹性返款比例记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			percentService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
