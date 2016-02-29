package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.LevelDao;
import com.entity.Level;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class LevelDaoImpl extends BaseDAO implements LevelDao {

	public void createLevel(Level level) {
		super.create(level);
	}

	public void updateLevel(Level level) {
		super.update(level);

	}

	public void deleteLevel(Level level) {
		super.delete(level);
	}
	
	public void deleteLevelById(Integer id) {
		super.delete((Level) super.loadByKey(Level.class,  "id", id));
	}

	public Level getLevelById(Integer id) {
		return (Level) super.loadByKey(Level.class,  "id", id);
	}

	public Level getLevelByMap(Map<String, Object> map) {
		return (Level) super.loadByKey(Level.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Level> getLevelList() {
		return super.loadAll(Level.class);
	}

	@SuppressWarnings("unchecked")
	public List<Level> getLevelListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Level.class, map);
	}

	@Override
	public PageBean getLevelPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Level.class, page, map);
	}

	public PageBean getLevelPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Level> getLevelListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
