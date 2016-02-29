package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Price;

/**
 * PriceDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface PriceDao {

	//创建price
	public void createPrice(Price price);
	
	//更新price
	public void updatePrice(Price price);
	
	//删除price
	public void deletePrice(Price price);
	
	//通过Id删除price
	public void deletePriceById(Integer id);
	
	//根据map查询price
	public Price getPriceById(Integer id);
	
	//根据map查询price
	public Price getPriceByMap(Map<String, Object> map);
	
	//查询price列表
	public List<Price> getPriceList();

	//根据map查询Price列表
	public List<Price> getPriceListByMap(Map<String, Object> map);
	
	//查询带翻页效果的price PageBean
	public PageBean getPricePageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getPricePageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Price> getPriceListByHQL(String hql);

}
