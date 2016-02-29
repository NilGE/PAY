package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Percent;

/**
 * PercentDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface PercentDao {

	//创建percent
	public void createPercent(Percent percent);
	
	//更新percent
	public void updatePercent(Percent percent);
	
	//删除percent
	public void deletePercent(Percent percent);
	
	//通过Id删除percent
	public void deletePercentById(Integer id);
	
	//根据map查询percent
	public Percent getPercentById(Integer id);
	
	//根据map查询percent
	public Percent getPercentByMap(Map<String, Object> map);
	
	//查询percent列表
	public List<Percent> getPercentList();

	//根据map查询Percent列表
	public List<Percent> getPercentListByMap(Map<String, Object> map);
	
	//查询带翻页效果的percent PageBean
	public PageBean getPercentPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getPercentPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Percent> getPercentListByHQL(String hql);

}
