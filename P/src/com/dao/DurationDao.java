package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Duration;

/**
 * DurationDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface DurationDao {

	//创建duration
	public void createDuration(Duration duration);
	
	//更新duration
	public void updateDuration(Duration duration);
	
	//删除duration
	public void deleteDuration(Duration duration);
	
	//通过Id删除duration
	public void deleteDurationById(Integer id);
	
	//根据map查询duration
	public Duration getDurationById(Integer id);
	
	//根据map查询duration
	public Duration getDurationByMap(Map<String, Object> map);
	
	//查询duration列表
	public List<Duration> getDurationList();

	//根据map查询Duration列表
	public List<Duration> getDurationListByMap(Map<String, Object> map);
	
	//查询带翻页效果的duration PageBean
	public PageBean getDurationPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getDurationPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Duration> getDurationListByHQL(String hql);

}
