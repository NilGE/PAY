package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Percent;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface PercentService {
	
	public Percent getPercentById(Integer id);
	
	public List<Percent> getPercentList();

	public void createPercent(Percent percent);

	public void deletePercentById(Integer id);

	public void updatePercent(Percent percent);
	
	public Percent getPercentByMap(Map<String, Object> map);
	
	public List<Percent> getPercentListByMap(Map<String, Object> map);

	public List<Percent> getPercentListByHQL(String hql);
	
	public PageBean getPercentPageByMap(Page page, Map<String, Object> map);
	
	public PageBean getPercentPageByHQL(Page page, String hql);
	/**
	 * 导入Excel数据
	 * @param targetFile
	 */
	public void importExcel(File targetFile);

	/**
	 * 删除全部数据
	 * @param ids
	 */
	public void deleteAll(String ids);

	/**
	 * 导出全部数据
	 * @param outputStream
	 */
	public void exportExcel(ServletOutputStream outputStream);
}
