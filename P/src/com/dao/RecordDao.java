package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Record;

/**
 * RecordDao层接口
 * 
 * @author admin
 * @version 2015-04-07 16:54:35
 */
public interface RecordDao {

	//创建record
	public void createRecord(Record record);
	
	//更新record
	public void updateRecord(Record record);
	
	//删除record
	public void deleteRecord(Record record);
	
	//通过Id删除record
	public void deleteRecordById(Integer id);
	
	//根据map查询record
	public Record getRecordById(Integer id);
	
	//根据map查询record
	public Record getRecordByMap(Map<String, Object> map);
	
	//查询record列表
	public List<Record> getRecordList();

	//根据map查询Record列表
	public List<Record> getRecordListByMap(Map<String, Object> map);
	
	//查询带翻页效果的record PageBean
	public PageBean getRecordPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getRecordPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Record> getRecordListByHQL(String hql);

}
