package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Standard;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface StandardService {
	
	public Standard getStandardById(Integer id);
	
	public List<Standard> getStandardList();

	public void createStandard(Standard standard);

	public void deleteStandardById(Integer id);

	public void updateStandard(Standard standard);
	
	public Standard getStandardByMap(Map<String, Object> map);
	
	public List<Standard> getStandardListByMap(Map<String, Object> map);

	public List<Standard> getStandardListByHQL(String hql);
	
	public PageBean getStandardPageByMap(Page page, Map<String, Object> map);
	
	public PageBean getStandardPageByHQL(Page page, String hql);
	
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
