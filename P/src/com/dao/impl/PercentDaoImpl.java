package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.PercentDao;
import com.entity.Percent;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class PercentDaoImpl extends BaseDAO implements PercentDao {

	public void createPercent(Percent percent) {
		super.create(percent);
	}

	public void updatePercent(Percent percent) {
		super.update(percent);

	}

	public void deletePercent(Percent percent) {
		super.delete(percent);
	}
	
	public void deletePercentById(Integer id) {
		super.delete((Percent) super.loadByKey(Percent.class,  "id", id));
	}

	public Percent getPercentById(Integer id) {
		return (Percent) super.loadByKey(Percent.class,  "id", id);
	}

	public Percent getPercentByMap(Map<String, Object> map) {
		return (Percent) super.loadByKey(Percent.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Percent> getPercentList() {
		return super.loadAll(Percent.class);
	}

	@SuppressWarnings("unchecked")
	public List<Percent> getPercentListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Percent.class, map);
	}

	@Override
	public PageBean getPercentPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Percent.class, page, map);
	}

	public PageBean getPercentPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Percent> getPercentListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
