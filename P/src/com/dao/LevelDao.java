package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Level;

/**
 * LevelDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface LevelDao {

	//创建level
	public void createLevel(Level level);
	
	//更新level
	public void updateLevel(Level level);
	
	//删除level
	public void deleteLevel(Level level);
	
	//通过Id删除level
	public void deleteLevelById(Integer id);
	
	//根据map查询level
	public Level getLevelById(Integer id);
	
	//根据map查询level
	public Level getLevelByMap(Map<String, Object> map);
	
	//查询level列表
	public List<Level> getLevelList();

	//根据map查询Level列表
	public List<Level> getLevelListByMap(Map<String, Object> map);
	
	//查询带翻页效果的level PageBean
	public PageBean getLevelPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getLevelPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Level> getLevelListByHQL(String hql);

}
