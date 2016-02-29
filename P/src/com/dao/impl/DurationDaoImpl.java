package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.DurationDao;
import com.entity.Duration;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class DurationDaoImpl extends BaseDAO implements DurationDao {

	public void createDuration(Duration duration) {
		super.create(duration);
	}

	public void updateDuration(Duration duration) {
		super.update(duration);

	}

	public void deleteDuration(Duration duration) {
		super.delete(duration);
	}
	
	public void deleteDurationById(Integer id) {
		super.delete((Duration) super.loadByKey(Duration.class,  "id", id));
	}

	public Duration getDurationById(Integer id) {
		return (Duration) super.loadByKey(Duration.class,  "id", id);
	}

	public Duration getDurationByMap(Map<String, Object> map) {
		return (Duration) super.loadByKey(Duration.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Duration> getDurationList() {
		return super.loadAll(Duration.class);
	}

	@SuppressWarnings("unchecked")
	public List<Duration> getDurationListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Duration.class, map);
	}

	@Override
	public PageBean getDurationPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Duration.class, page, map);
	}

	public PageBean getDurationPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Duration> getDurationListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
