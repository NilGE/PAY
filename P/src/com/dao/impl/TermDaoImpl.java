package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.dao.TermDao;
import com.entity.Term;

/**
 * Dao层接口实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Repository
public class TermDaoImpl extends BaseDAO implements TermDao {

	public void createTerm(Term term) {
		super.create(term);
	}

	public void updateTerm(Term term) {
		super.update(term);

	}

	public void deleteTerm(Term term) {
		super.delete(term);
	}
	
	public void deleteTermById(Integer id) {
		super.delete((Term) super.loadByKey(Term.class,  "id", id));
	}

	public Term getTermById(Integer id) {
		return (Term) super.loadByKey(Term.class,  "id", id);
	}

	public Term getTermByMap(Map<String, Object> map) {
		return (Term) super.loadByKey(Term.class, map);
	}

	@SuppressWarnings("unchecked")
	public List<Term> getTermList() {
		return super.loadAll(Term.class);
	}

	@SuppressWarnings("unchecked")
	public List<Term> getTermListByMap(Map<String, Object> map) {
		return super.loadAllByKey(Term.class, map);
	}

	@Override
	public PageBean getTermPageByMap(Page page, Map<String, Object> map) {
		return super.loadAllByKeyPage(Term.class, page, map);
	}

	public PageBean getTermPageByHQL(Page page, String hql) {
		return super.queryPageByHQL(page, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Term> getTermListByHQL(String hql) {
		return super.queryByHQL(hql);
	}

}
