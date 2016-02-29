package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.RecordDao;
import com.entity.Record;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 16:54:35
 */
@Repository
public class RecordDaoImpl extends BaseDAO implements RecordDao {

	public void createRecord(Record record) {
		super.create(record);
	}

	public void updateRecord(Record record) {
		super.update(record);

	}

	public void deleteRecord(Record record) {
		super.delete(record);
	}
	
	public void deleteRecordById(Integer id) {
		super.delete((Record) super.loadByKey(Record.class,  "id", id));
	}

	public Record getRecordById(Integer id) {
		return (Record) super.loadByKey(Record.class,  "id", id);
	}

	public Record getRecordByMap(Map<String, Object> map) {
		return (Record) super.loadByKey(Record.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Record> getRecordList() {
		return super.loadAll(Record.class);
	}

	@SuppressWarnings("unchecked")
	public List<Record> getRecordListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Record.class, map);
	}

	@Override
	public PageBean getRecordPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Record.class, page, map);
	}

	public PageBean getRecordPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Record> getRecordListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
