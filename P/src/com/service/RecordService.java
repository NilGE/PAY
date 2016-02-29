package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Record;
import com.entity.User;
import com.service.entiy.CampusPay;

/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 16:54:35
 */
public interface RecordService {

	public Record getRecordById(Integer id);

	public List<Record> getRecordList();

	public void createRecord(Record record);

	public void deleteRecordById(Integer id);

	public void updateRecord(Record record);

	public Record getRecordByMap(Map<String, Object> map);

	public List<Record> getRecordListByMap(Map<String, Object> map);

	public List<Record> getRecordListByHQL(String hql);

	public PageBean getRecordPageByMap(Page page, Map<String, Object> map);

	public PageBean getRecordPageByHQL(Page page, String hql);

	/**
	 * 上传文件后使用POI处理
	 * 
	 * @param targetFile
	 */
	public void importExcel(File targetFile);

	/**
	 * 导出Excel
	 * 
	 * @param user
	 * 
	 * @param outputStream
	 */
	public void exportExcel(User user, ServletOutputStream outputStream);

	/**
	 * 计算各个学习中心应该返款金额
	 * 
	 * @param map
	 * @return
	 */
	public List<CampusPay> calculate(Map<String, Object> map);

	/**
	 * 删除所有记录
	 * 
	 * @param ids
	 */
	public void deleteAll(String ids);

	/**
	 * 导出返款金额
	 * 
	 * @param u
	 * @param outputStream
	 */
	public void exportBackMoney(User u, ServletOutputStream outputStream);

	/**
	 * 导出报表
	 * 
	 * @param u
	 * @param outputStream
	 */
	public void exportFile(User u, ServletOutputStream outputStream);
}
