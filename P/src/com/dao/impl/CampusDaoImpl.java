package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.CampusDao;
import com.entity.Campus;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class CampusDaoImpl extends BaseDAO implements CampusDao {

	public void createCampus(Campus campus) {
		super.create(campus);
	}

	public void updateCampus(Campus campus) {
		super.update(campus);

	}

	public void deleteCampus(Campus campus) {
		super.delete(campus);
	}
	
	public void deleteCampusById(Integer id) {
		super.delete((Campus) super.loadByKey(Campus.class,  "id", id));
	}

	public Campus getCampusById(Integer id) {
		return (Campus) super.loadByKey(Campus.class,  "id", id);
	}

	public Campus getCampusByMap(Map<String, Object> map) {
		return (Campus) super.loadByKey(Campus.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Campus> getCampusList() {
		return super.loadAll(Campus.class);
	}

	@SuppressWarnings("unchecked")
	public List<Campus> getCampusListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Campus.class, map);
	}

	@Override
	public PageBean getCampusPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Campus.class, page, map);
	}

	public PageBean getCampusPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Campus> getCampusListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
