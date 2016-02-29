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
import com.entity.Price;
import com.entity.User;
import com.service.PriceService;
import com.service.CampusService;
import com.service.MajorService;
import com.service.LevelService;
import com.service.TermService;
import com.service.UserService;
import com.util.ProjectConfig;

/**
 * Price控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("price")
public class PriceController {

	@Resource
	private PriceService priceService;
	
	@Resource
	private UserService userService;

	@Resource
	private CampusService campusService;

	@Resource
	private MajorService majorService;

	@Resource
	private LevelService levelService;

	@Resource
	private TermService termService;

	@RequestMapping("view_price")
	public String view_price(Page page, PageUtil pu, HttpServletRequest request) {
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		User user = userService.getUserById(user_id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		if ("PAY".equals(user.getGroup().getGroupCode())) {
			map.put("campus.id", user.getCampus().getId());
		}
		List<Price> list = priceService.getPriceListByMap(map);
		pu.init(list, pu.getSize());
		pu.setAction("../price/view_price");
		request.setAttribute("pu", pu);

		PageBean pageBean = priceService.getPricePageByMap(page, map);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("campusList", campusService.getCampusList());
		request.setAttribute("majorList", majorService.getMajorList());
		request.setAttribute("levelList", levelService.getLevelList());
		request.setAttribute("termList", termService.getTermList());
		request.setAttribute("nav_tag", "PAYMENT");
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_price";
	}

	@RequestMapping("/addPrice")
	public String addPrice(Price price, HttpServletRequest request) {
		priceService.createPrice(price);
		return "redirect:/price/view_price";
	}

	// @RequestMapping("/deletePrice")
	@RequestMapping(value = "/deletePrice")
	public String deletePrice(Integer id, HttpServletResponse response) {
		priceService.deletePriceById(id);
		return "redirect:/price/view_price";
	}

	@RequestMapping("/updatePrice")
	public String updatePrice(Price price, HttpServletRequest request) {
		priceService.updatePrice(price);
		return "redirect:/price/view_price";
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
				priceService.importExcel(targetFile);

			}
		}
		return "redirect:/price/view_price";
	}

	@RequestMapping("/deleteAllPrice")
	public String deleteAllPrice(String ids, HttpServletResponse response) {
		priceService.deleteAll(ids);
		return "redirect:/price/view_price";
	}

	/**
	 * Export price data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部学费标准记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			priceService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
