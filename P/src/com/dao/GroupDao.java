package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Group;

/**
 * GroupDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface GroupDao {

	//创建group
	public void createGroup(Group group);
	
	//更新group
	public void updateGroup(Group group);
	
	//删除group
	public void deleteGroup(Group group);
	
	//通过Id删除group
	public void deleteGroupById(Integer id);
	
	//根据map查询group
	public Group getGroupById(Integer id);
	
	//根据map查询group
	public Group getGroupByMap(Map<String, Object> map);
	
	//查询group列表
	public List<Group> getGroupList();

	//根据map查询Group列表
	public List<Group> getGroupListByMap(Map<String, Object> map);
	
	//查询带翻页效果的group PageBean
	public PageBean getGroupPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getGroupPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Group> getGroupListByHQL(String hql);

}
