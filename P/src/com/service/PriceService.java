package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Price;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface PriceService {
	
	public Price getPriceById(Integer id);
	
	public List<Price> getPriceList();

	public void createPrice(Price price);

	public void deletePriceById(Integer id);

	public void updatePrice(Price price);
	
	public Price getPriceByMap(Map<String, Object> map);
	
	public List<Price> getPriceListByMap(Map<String, Object> map);

	public List<Price> getPriceListByHQL(String hql);
	
	public PageBean getPricePageByMap(Page page, Map<String, Object> map);
	
	public PageBean getPricePageByHQL(Page page, String hql);
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
