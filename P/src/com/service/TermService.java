package com.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.entity.Term;
/**
 * Service层接口
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
public interface TermService {
	
	public Term getTermById(Integer id);
	
	public List<Term> getTermList();

	public void createTerm(Term term);

	public void deleteTermById(Integer id);

	public void updateTerm(Term term);
	
	public Term getTermByMap(Map<String, Object> map);
	
	public List<Term> getTermListByMap(Map<String, Object> map);

	public List<Term> getTermListByHQL(String hql);
	
	public PageBean getTermPageByMap(Page page, Map<String, Object> map);
	
	public PageBean getTermPageByHQL(Page page, String hql);
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
