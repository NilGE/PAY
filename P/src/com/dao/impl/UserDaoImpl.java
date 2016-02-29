package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.UserDao;
import com.entity.User;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class UserDaoImpl extends BaseDAO implements UserDao {

	public void createUser(User user) {
		super.create(user);
	}

	public void updateUser(User user) {
		super.update(user);

	}

	public void deleteUser(User user) {
		super.delete(user);
	}
	
	public void deleteUserById(Integer id) {
		super.delete((User) super.loadByKey(User.class,  "id", id));
	}

	public User getUserById(Integer id) {
		return (User) super.loadByKey(User.class,  "id", id);
	}

	public User getUserByMap(Map<String, Object> map) {
		return (User) super.loadByKey(User.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
		return super.loadAll(User.class);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserListByMap(Map<String, Object> map) {
		return super.loadAllByKey(User.class, map);
	}

	@Override
	public PageBean getUserPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(User.class, page, map);
	}

	public PageBean getUserPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
