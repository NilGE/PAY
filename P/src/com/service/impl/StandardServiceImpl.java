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
import com.dao.StandardDao;
import com.entity.Standard;
import com.service.StandardService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class StandardServiceImpl implements StandardService {

	@Resource
	private StandardDao standardDao;


	public void createStandard(Standard standard) {
		standardDao.createStandard(standard);
	}

	public void deleteStandardById(Integer id) {
		Standard c = standardDao.getStandardById(id);
		c.setDisabled(true);
		standardDao.updateStandard(c);
		// standardDao.deleteStandardById(id);
	}

	public List<Standard> getStandardList() {
		return standardDao.getStandardList();
	}

	public void updateStandard(Standard standard) {
		standardDao.updateStandard(standard);
	}

	public Standard getStandardById(Integer id) {
		return standardDao.getStandardById(id);
	}
	
	public Standard getStandardByMap(Map<String, Object> map) {	
		return standardDao.getStandardByMap(map);
	}

	public List<Standard> getStandardListByMap(Map<String, Object> map) {
		return standardDao.getStandardListByMap(map);
	}
	
	public List<Standard> getStandardListByHQL(String hql) {
		return standardDao.getStandardListByHQL(hql);
	}

	public PageBean getStandardPageByMap(Page page, Map<String, Object> map) {
		return standardDao.getStandardPageByMap(page, map);
	}

	public PageBean getStandardPageByHQL(Page page, String hql) {
		return standardDao.getStandardPageByHQL(page, hql);
	}
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Standard> list = standardDao.getStandardListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "ID","学分", "学期" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Standard c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getId());
				bodyRow.createCell(1).setCellValue(c.getCredit());
				bodyRow.createCell(2).setCellValue(c.getTermContent());

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
	 * @param standardCode
	 * @return
	 */
	private boolean checkStandard(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return StringUtil.checkNull(standardDao.getStandardByMap(map));
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

				int id = (int)row.getCell(0).getNumericCellValue();
				boolean b = checkStandard(id);
				if (b) {
					Standard c = new Standard();
					c.setCredit((int)row.getCell(1).getNumericCellValue());
					c.setTermContent(StringUtil.checkString(row.getCell(2).getStringCellValue()));
					standardDao.createStandard(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					Standard c = standardDao.getStandardByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					c.setCredit((int)row.getCell(1).getNumericCellValue());
					c.setTermContent(StringUtil.checkString(row.getCell(2).getStringCellValue()));
					standardDao.updateStandard(c);
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
					Standard c = standardDao.getStandardById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					standardDao.updateStandard(c);
					// standardDao.deleteStandardById(Integer.valueOf(idArray[i]));
				}
			} else {
				Standard c = standardDao.getStandardById(Integer.valueOf(ids));
				c.setDisabled(true);
				standardDao.updateStandard(c);
				// standardDao.deleteStandardById(Integer.valueOf(ids));
			}
		}

	}

}
