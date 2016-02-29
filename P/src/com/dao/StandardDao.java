package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Standard;

/**
 * StandardDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface StandardDao {

	//创建standard
	public void createStandard(Standard standard);
	
	//更新standard
	public void updateStandard(Standard standard);
	
	//删除standard
	public void deleteStandard(Standard standard);
	
	//通过Id删除standard
	public void deleteStandardById(Integer id);
	
	//根据map查询standard
	public Standard getStandardById(Integer id);
	
	//根据map查询standard
	public Standard getStandardByMap(Map<String, Object> map);
	
	//查询standard列表
	public List<Standard> getStandardList();

	//根据map查询Standard列表
	public List<Standard> getStandardListByMap(Map<String, Object> map);
	
	//查询带翻页效果的standard PageBean
	public PageBean getStandardPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getStandardPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Standard> getStandardListByHQL(String hql);

}
