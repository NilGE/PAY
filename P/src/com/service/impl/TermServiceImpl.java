package com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.common.tool.util.StringUtil;
import com.dao.TermDao;
import com.entity.Term;
import com.service.TermService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class TermServiceImpl implements TermService {

	@Resource
	private TermDao termDao;


	public void createTerm(Term term) {
		termDao.createTerm(term);
	}

	public void deleteTermById(Integer id) {
		Term c = termDao.getTermById(id);
		c.setDisabled(true);
		termDao.updateTerm(c);
		// termDao.deleteTermById(id);
	}

	public List<Term> getTermList() {
		return termDao.getTermList();
	}

	public void updateTerm(Term term) {
		termDao.updateTerm(term);
	}

	public Term getTermById(Integer id) {
		return termDao.getTermById(id);
	}
	
	public Term getTermByMap(Map<String, Object> map) {	
		return termDao.getTermByMap(map);
	}

	public List<Term> getTermListByMap(Map<String, Object> map) {
		return termDao.getTermListByMap(map);
	}
	
	public List<Term> getTermListByHQL(String hql) {
		return termDao.getTermListByHQL(hql);
	}

	public PageBean getTermPageByMap(Page page, Map<String, Object> map) {
		return termDao.getTermPageByMap(page, map);
	}

	public PageBean getTermPageByHQL(Page page, String hql) {
		return termDao.getTermPageByHQL(page, hql);
	}
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Term> list = termDao.getTermListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "批次代码", "批次名称" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Term c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getTermCode());
				bodyRow.createCell(1).setCellValue(c.getTermName());

			}
		}

		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 检测#entityName#是否存在
	 * 
	 * @param termCode
	 * @return
	 */
	private boolean checkTerm(String termCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termCode", termCode);
		return StringUtil.checkNull(termDao.getTermByMap(map));
	}

	@Override
	public void importExcel(File targetFile) {
		// 创建相关的文件流对象
		FileInputStream in;
		try {
			in = new FileInputStream(targetFile);
			// 声明相关的工作薄对象
			Workbook wb = null;
			String name = targetFile.getName();
			String ext = name.substring(name.lastIndexOf("."), name.length());
			System.out.println("扩展名" + ext);
			Sheet sheet = null;
			if (ext.equals(".xls"))// 针对2003版本
			{
				// 创建excel2003的文件文本抽取对象
				wb = new HSSFWorkbook(new POIFSFileSystem(in));
				sheet = (HSSFSheet) wb.getSheetAt(0);
			} else { // 针对2007版本
				wb = new XSSFWorkbook(in);
				sheet = (XSSFSheet) wb.getSheetAt(0);
			}

			System.out.println("totalRow:" + sheet.getLastRowNum());
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				String termCode = row.getCell(0).getStringCellValue();
				boolean b = checkTerm(termCode);
				if (b) {
					Term c = new Term();
					c.setTermCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					c.setTermName(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					termDao.createTerm(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("termCode", termCode);
					Term c = termDao.getTermByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					c.setTermCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					c.setTermName(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					termDao.updateTerm(c);
				}
			}
			System.out.println("数据导入成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除所有记录
	 */
	@Override
	public void deleteAll(String ids) {
		System.out.println(ids);
		if (!StringUtil.checkNull(ids)) {
			if (ids.contains(",")) {
				String[] idArray = ids.split(",");
				for (int i = 0; i < idArray.length; i++) {
					Term c = termDao.getTermById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					termDao.updateTerm(c);
					// termDao.deleteTermById(Integer.valueOf(idArray[i]));
				}
			} else {
				Term c = termDao.getTermById(Integer.valueOf(ids));
				c.setDisabled(true);
				termDao.updateTerm(c);
				// termDao.deleteTermById(Integer.valueOf(ids));
			}
		}

	}

}
