package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Duration;

/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface DurationService {

	public Duration getDurationById(Integer id);

	public List<Duration> getDurationList();

	public void createDuration(Duration duration);

	public void deleteDurationById(Integer id);

	public void updateDuration(Duration duration);

	public Duration getDurationByMap(Map<String, Object> map);

	public List<Duration> getDurationListByMap(Map<String, Object> map);

	public List<Duration> getDurationListByHQL(String hql);

	public PageBean getDurationPageByMap(Page page, Map<String, Object> map);

	public PageBean getDurationPageByHQL(Page page, String hql);

	/**
	 * 导入Excel数据
	 * 
	 * @param targetFile
	 */
	public void importExcel(File targetFile);

	/**
	 * 删除全部数据
	 * 
	 * @param ids
	 */
	public void deleteAll(String ids);

	/**
	 * 导出全部数据
	 * 
	 * @param outputStream
	 */
	public void exportExcel(ServletOutputStream outputStream);
}
