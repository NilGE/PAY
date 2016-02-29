package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.User;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface UserService {
	
	public User getUserById(Integer id);
	
	public List<User> getUserList();

	public void createUser(User user);

	public void deleteUserById(Integer id);

	public void updateUser(User user);
	
	public User getUserByMap(Map<String, Object> map);
	
	public List<User> getUserListByMap(Map<String, Object> map);

	public List<User> getUserListByHQL(String hql);
	
	public PageBean getUserPageByMap(Page page, Map<String, Object> map);
	
	public PageBean getUserPageByHQL(Page page, String hql);

	/**
	 * @return
	 */
	public boolean checkInit();

	/**
	 * @param username
	 * @param password
	 */
	public void init(String username, String password);

	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkLogin(String username, String password);
	/**
	 * 导入Excel数据
	 * @param targetFile
	 */
	public void importExcel(File targetFile);

	/**
	 * 删除全部数据
	 * @param ids
	 */
	public void deleteAll(String ids);

	/**
	 * 导出全部数据
	 * @param outputStream
	 */
	public void exportExcel(ServletOutputStream outputStream);
}
