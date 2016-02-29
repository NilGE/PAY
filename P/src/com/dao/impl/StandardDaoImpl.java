package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.StandardDao;
import com.entity.Standard;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class StandardDaoImpl extends BaseDAO implements StandardDao {

	public void createStandard(Standard standard) {
		super.create(standard);
	}

	public void updateStandard(Standard standard) {
		super.update(standard);

	}

	public void deleteStandard(Standard standard) {
		super.delete(standard);
	}
	
	public void deleteStandardById(Integer id) {
		super.delete((Standard) super.loadByKey(Standard.class,  "id", id));
	}

	public Standard getStandardById(Integer id) {
		return (Standard) super.loadByKey(Standard.class,  "id", id);
	}

	public Standard getStandardByMap(Map<String, Object> map) {
		return (Standard) super.loadByKey(Standard.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Standard> getStandardList() {
		return super.loadAll(Standard.class);
	}

	@SuppressWarnings("unchecked")
	public List<Standard> getStandardListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Standard.class, map);
	}

	@Override
	public PageBean getStandardPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Standard.class, page, map);
	}

	public PageBean getStandardPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Standard> getStandardListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
