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
import com.common.tool.util.PageUtil;
import com.common.tool.util.StringUtil;
import com.entity.User;
import com.service.CampusService;
import com.service.GroupService;
import com.service.UserService;
import com.util.ProjectConfig;

/**
 * User控制器
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private GroupService groupService;

	@Resource
	private CampusService campusService;

	/**
	 * 系统初始化
	 * 
	 * @author neuhxy
	 */
	@RequestMapping("/init")
	public String init(User user, HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		boolean b = userService.checkInit();
		System.out.println("是否需要初始化：" + b);
		if (!StringUtil.checkNull(user.getUsername()) && b) {
			userService.init(user.getUsername(), user.getPassword());
			return "admin/login";
		} else {
			return "admin/login";
		}

	}

	@RequestMapping("login")
	public String admin_login(User user, HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/login";
	}

	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/index";
	}

	/**
	 * 管理员登录
	 * 
	 * @author neuhxy
	 */
	@RequestMapping("adminLogin")
	public String adminLogin(User admin, HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		if (!StringUtil.checkNull(admin.getUsername())) {
			String username = admin.getUsername();
			String password = admin.getPassword();
			System.out.println(username + " " + password);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);
			map.put("disabled", false);
			admin = userService.getUserByMap(map);
			boolean b = userService.checkLogin(username, password);
			if (b) {
				request.getSession().invalidate();
				request.getSession().setAttribute("admin_login", "login");
				request.getSession().setAttribute("user_id", admin.getId());
				request.getSession().setAttribute("username", admin.getUsername());
				request.getSession().setAttribute("groupCode",admin.getGroup().getGroupCode());

			} else {
				return "admin/login";
			}
			return "redirect:/user/index";
		}
		return "admin/login";

	}

	/**
	 * 管理员注销，然后返回管理员登录首页
	 * 
	 * @author neuhxy
	 */
	@RequestMapping("adminLogout")
	public String adminLogout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/user/login";
	}

	@RequestMapping("view_user")
	public String view_user(PageUtil pu, HttpServletRequest request) {
		System.out.println(pu.getSize());
		request.setAttribute("nav_tag", "CONFIG");
		//分页组件用法：首先获取所有List
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<User> users = userService.getUserListByMap(map);
		pu.init(users, pu.getSize());
		
		pu.setAction("../user/view_user");

		request.setAttribute("pu", pu);
		
		//request.setAttribute("userList", userService.getUserList());
		request.setAttribute("groupList", groupService.getGroupList());
		request.setAttribute("campusList", campusService.getCampusList());
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		return "admin/view_user";
	}

	@RequestMapping("/addUser")
	public String addUser(User user, HttpServletRequest request) {
		userService.createUser(user);
		return "redirect:/user/view_user";
	}

	// @RequestMapping("/deleteUser")
	@RequestMapping(value = "/deleteUser")
	public String deleteUser(Integer id, HttpServletResponse response) {
		userService.deleteUserById(id);
		return "redirect:/user/view_user";
	}

	@RequestMapping("/updateUser")
	public String updateUser(User user, HttpServletRequest request) {
		userService.updateUser(user);
		return "redirect:/user/view_user";
	}

	@RequestMapping("/view_password")
	public String view_password(HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		request.setAttribute("nav_tag", "CONFIG");
		return "admin/view_password";
	}

	/**
	 * 管理员为各个学习中心的财务用户重置密码，默认密码为123456
	 * 
	 * @param username
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Object resetPwd(String user_id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "重置密码未成功！");
		System.out.println("重置密码："+user_id);
		if (!StringUtil.checkNull(user_id)) {
			int u_id = Integer.valueOf(user_id);
			User admin = userService.getUserById(u_id);
			admin.setPassword(StringUtil.md5(ProjectConfig.DEFAULT_PASSWORD));
			userService.updateUser(admin);
			map.put("msg", "重置密码成功！");
		}
		return map;

	}

	@RequestMapping("/updatePwd")
	public String updatePwd(String oldPwd, String newPwd,
			HttpServletRequest request) {
		request.setAttribute("PROJECT_TITLE", ProjectConfig.PROJECT_TITLE);
		request.setAttribute("nav_tag", "CONFIG");
		Integer user_id = (Integer) request.getSession()
				.getAttribute("user_id");
		User admin = userService.getUserById(user_id);
		boolean b = userService.checkLogin(admin.getUsername(), oldPwd);
		if (b) {
			// 旧密码验证通过
			admin.setPassword(StringUtil.md5(newPwd));
			userService.updateUser(admin);
			request.setAttribute("message", "密码修改成功！");
		} else {
			request.setAttribute("message", "原始密码验证失败！");
		}
		return "admin/view_password";
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
				userService.importExcel(targetFile);

			}
		}
		return "redirect:/user/view_user";
	}
	
	
	@RequestMapping("/deleteAllUser")
	public String deleteAllUser(String ids, HttpServletResponse response) {
		userService.deleteAll(ids);
		return "redirect:/user/view_user";
	}
	/**
	 * Export user data into excel
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletResponse response) {
		response.setContentType("application/binary;charset=utf-8");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(("全部用户记录").getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName +DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xlsx");// 组装附件名称和格式

			userService.exportExcel(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
