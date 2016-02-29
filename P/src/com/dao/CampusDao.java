package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Campus;

/**
 * CampusDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface CampusDao {

	// 创建campus
	public void createCampus(Campus campus);

	// 更新campus
	public void updateCampus(Campus campus);

	// 删除campus
	public void deleteCampus(Campus campus);

	// 通过Id删除campus
	public void deleteCampusById(Integer id);

	// 根据map查询campus
	public Campus getCampusById(Integer id);

	// 根据map查询campus
	public Campus getCampusByMap(Map<String, Object> map);

	// 查询campus列表
	public List<Campus> getCampusList();

	// 根据map查询Campus列表
	public List<Campus> getCampusListByMap(Map<String, Object> map);

	// 查询带翻页效果的campus PageBean
	public PageBean getCampusPageByMap(Page page, Map<String, Object> map);

	// 通过hql获取PageBean
	public PageBean getCampusPageByHQL(Page page, String hql);

	// 通过HQL获取List
	public List<Campus> getCampusListByHQL(String hql);

}
