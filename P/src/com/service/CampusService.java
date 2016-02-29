package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Campus;

/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface CampusService {

	public Campus getCampusById(Integer id);

	public List<Campus> getCampusList();

	public void createCampus(Campus campus);

	public void deleteCampusById(Integer id);

	public void updateCampus(Campus campus);

	public Campus getCampusByMap(Map<String, Object> map);

	public List<Campus> getCampusListByMap(Map<String, Object> map);

	public List<Campus> getCampusListByHQL(String hql);

	public PageBean getCampusPageByMap(Page page, Map<String, Object> map);

	public PageBean getCampusPageByHQL(Page page, String hql);

	/**
	 * Export campus data into excel file.
	 * 
	 * @param outputStream
	 */
	public void exportExcel(ServletOutputStream outputStream);

	/**
	 * Import campus data from excel file.
	 * 
	 * @param targetFile
	 */
	public void importExcel(File targetFile);

	/**
	 * Delete All campus data with id equals ids array.
	 * 
	 * @param ids
	 */
	public void deleteAll(String ids);
}
