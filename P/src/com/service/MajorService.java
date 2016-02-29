package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Major;

/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface MajorService {

	public Major getMajorById(Integer id);

	public List<Major> getMajorList();

	public void createMajor(Major major);

	public void deleteMajorById(Integer id);

	public void updateMajor(Major major);

	public Major getMajorByMap(Map<String, Object> map);

	public List<Major> getMajorListByMap(Map<String, Object> map);

	public List<Major> getMajorListByHQL(String hql);

	public PageBean getMajorPageByMap(Page page, Map<String, Object> map);

	public PageBean getMajorPageByHQL(Page page, String hql);

	/**
	 * 导出所有专业
	 * 
	 * @param outputStream
	 */
	public void exportExcel(ServletOutputStream outputStream);

	/**
	 * import major data from excel file.
	 * 
	 * @param targetFile
	 */
	public void importExcel(File targetFile);

	/**
	 * 删除全部专业
	 * 
	 * @param ids
	 */
	public void deleteAll(String ids);
}
