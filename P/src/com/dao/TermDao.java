package com.dao;

import java.util.List;
import java.util.Map;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Term;

/**
 * TermDao层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface TermDao {

	//创建term
	public void createTerm(Term term);
	
	//更新term
	public void updateTerm(Term term);
	
	//删除term
	public void deleteTerm(Term term);
	
	//通过Id删除term
	public void deleteTermById(Integer id);
	
	//根据map查询term
	public Term getTermById(Integer id);
	
	//根据map查询term
	public Term getTermByMap(Map<String, Object> map);
	
	//查询term列表
	public List<Term> getTermList();

	//根据map查询Term列表
	public List<Term> getTermListByMap(Map<String, Object> map);
	
	//查询带翻页效果的term PageBean
	public PageBean getTermPageByMap(Page page, Map<String, Object> map);
	
	//通过hql获取PageBean
	public PageBean getTermPageByHQL(Page page, String hql);
	
	//通过HQL获取List
	public List<Term> getTermListByHQL(String hql);

}
