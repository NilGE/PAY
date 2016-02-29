package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Level;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface LevelService {
	
	public Level getLevelById(Integer id);
	
	public List<Level> getLevelList();

	public void createLevel(Level level);

	public void deleteLevelById(Integer id);

	public void updateLevel(Level level);
	
	public Level getLevelByMap(Map<String, Object> map);
	
	public List<Level> getLevelListByMap(Map<String, Object> map);

	public List<Level> getLevelListByHQL(String hql);
	
	public PageBean getLevelPageByMap(Page page, Map<String, Object> map);
	
	public PageBean getLevelPageByHQL(Page page, String hql);
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
