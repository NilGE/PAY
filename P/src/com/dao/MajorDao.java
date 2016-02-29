package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Major;

/**
 * MajorDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface MajorDao {

	//创建major
	public void createMajor(Major major);
	
	//更新major
	public void updateMajor(Major major);
	
	//删除major
	public void deleteMajor(Major major);
	
	//通过Id删除major
	public void deleteMajorById(Integer id);
	
	//根据map查询major
	public Major getMajorById(Integer id);
	
	//根据map查询major
	public Major getMajorByMap(Map<String, Object> map);
	
	//查询major列表
	public List<Major> getMajorList();

	//根据map查询Major列表
	public List<Major> getMajorListByMap(Map<String, Object> map);
	
	//查询带翻页效果的major PageBean
	public PageBean getMajorPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getMajorPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Major> getMajorListByHQL(String hql);

}
