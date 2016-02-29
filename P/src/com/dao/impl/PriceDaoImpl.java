package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.PriceDao;
import com.entity.Price;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class PriceDaoImpl extends BaseDAO implements PriceDao {

	public void createPrice(Price price) {
		super.create(price);
	}

	public void updatePrice(Price price) {
		super.update(price);

	}

	public void deletePrice(Price price) {
		super.delete(price);
	}
	
	public void deletePriceById(Integer id) {
		super.delete((Price) super.loadByKey(Price.class,  "id", id));
	}

	public Price getPriceById(Integer id) {
		return (Price) super.loadByKey(Price.class,  "id", id);
	}

	public Price getPriceByMap(Map<String, Object> map) {
		return (Price) super.loadByKey(Price.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Price> getPriceList() {
		return super.loadAll(Price.class);
	}

	@SuppressWarnings("unchecked")
	public List<Price> getPriceListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Price.class, map);
	}

	@Override
	public PageBean getPricePageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Price.class, page, map);
	}

	public PageBean getPricePageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Price> getPriceListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
