package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.GroupDao;
import com.entity.Group;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class GroupDaoImpl extends BaseDAO implements GroupDao {

	public void createGroup(Group group) {
		super.create(group);
	}

	public void updateGroup(Group group) {
		super.update(group);

	}

	public void deleteGroup(Group group) {
		super.delete(group);
	}
	
	public void deleteGroupById(Integer id) {
		super.delete((Group) super.loadByKey(Group.class,  "id", id));
	}

	public Group getGroupById(Integer id) {
		return (Group) super.loadByKey(Group.class,  "id", id);
	}

	public Group getGroupByMap(Map<String, Object> map) {
		return (Group) super.loadByKey(Group.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Group> getGroupList() {
		return super.loadAll(Group.class);
	}

	@SuppressWarnings("unchecked")
	public List<Group> getGroupListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Group.class, map);
	}

	@Override
	public PageBean getGroupPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Group.class, page, map);
	}

	public PageBean getGroupPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Group> getGroupListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
