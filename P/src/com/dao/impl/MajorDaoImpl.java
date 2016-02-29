package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.MajorDao;
import com.entity.Major;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class MajorDaoImpl extends BaseDAO implements MajorDao {

	public void createMajor(Major major) {
		super.create(major);
	}

	public void updateMajor(Major major) {
		super.update(major);

	}

	public void deleteMajor(Major major) {
		super.delete(major);
	}
	
	public void deleteMajorById(Integer id) {
		super.delete((Major) super.loadByKey(Major.class,  "id", id));
	}

	public Major getMajorById(Integer id) {
		return (Major) super.loadByKey(Major.class,  "id", id);
	}

	public Major getMajorByMap(Map<String, Object> map) {
		return (Major) super.loadByKey(Major.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Major> getMajorList() {
		return super.loadAll(Major.class);
	}

	@SuppressWarnings("unchecked")
	public List<Major> getMajorListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Major.class, map);
	}

	@Override
	public PageBean getMajorPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Major.class, page, map);
	}

	public PageBean getMajorPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Major> getMajorListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
