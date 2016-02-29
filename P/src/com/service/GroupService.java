package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Group;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface GroupService {
	
	public Group getGroupById(Integer id);
	
	public List<Group> getGroupList();

	public void createGroup(Group group);

	public void deleteGroupById(Integer id);

	public void updateGroup(Group group);
	
	public Group getGroupByMap(Map<String, Object> map);
	
	public List<Group> getGroupListByMap(Map<String, Object> map);

	public List<Group> getGroupListByHQL(String hql);
	
	public PageBean getGroupPageByMap(Page page, Map<String, Object> map);
	
	public PageBean getGroupPageByHQL(Page page, String hql);
	
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
