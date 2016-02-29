package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.User;

/**
 * UserDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface UserDao {

	//创建user
	public void createUser(User user);
	
	//更新user
	public void updateUser(User user);
	
	//删除user
	public void deleteUser(User user);
	
	//通过Id删除user
	public void deleteUserById(Integer id);
	
	//根据map查询user
	public User getUserById(Integer id);
	
	//根据map查询user
	public User getUserByMap(Map<String, Object> map);
	
	//查询user列表
	public List<User> getUserList();

	//根据map查询User列表
	public List<User> getUserListByMap(Map<String, Object> map);
	
	//查询带翻页效果的user PageBean
	public PageBean getUserPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getUserPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<User> getUserListByHQL(String hql);

}
