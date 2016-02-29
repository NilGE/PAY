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
import com.dao.MajorDao;
import com.entity.Major;
import com.service.MajorService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Service
public class MajorServiceImpl implements MajorService {

	@Resource
	private MajorDao majorDao;

	public void createMajor(Major major) {
		majorDao.createMajor(major);
	}

	public void deleteMajorById(Integer id) {
		Major m = majorDao.getMajorById(id);
		m.setDisabled(true);
		majorDao.updateMajor(m);
		// majorDao.deleteMajorById(id);
	}

	public List<Major> getMajorList() {
		return majorDao.getMajorList();
	}

	public void updateMajor(Major major) {
		majorDao.updateMajor(major);
	}

	public Major getMajorById(Integer id) {
		return majorDao.getMajorById(id);
	}

	public Major getMajorByMap(Map<String, Object> map) {
		return majorDao.getMajorByMap(map);
	}

	public List<Major> getMajorListByMap(Map<String, Object> map) {
		return majorDao.getMajorListByMap(map);
	}

	public List<Major> getMajorListByHQL(String hql) {
		return majorDao.getMajorListByHQL(hql);
	}

	public PageBean getMajorPageByMap(Page page, Map<String, Object> map) {
		return majorDao.getMajorPageByMap(page, map);
	}

	public PageBean getMajorPageByHQL(Page page, String hql) {
		return majorDao.getMajorPageByHQL(page, hql);
	}

	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Major> list = majorDao.getMajorListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "专业代码", "专业名称" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Major m = list.get(i);

				bodyRow.createCell(0).setCellValue(m.getMajorCode());
				bodyRow.createCell(1).setCellValue(m.getMajorName());

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
	 * 检测专业是否存在
	 * 
	 * @param majorName
	 * @return
	 */
	private boolean checkMajor(String majorName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("majorName", majorName);
		return StringUtil.checkNull(majorDao.getMajorByMap(map));
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

				String majorName = row.getCell(1).getStringCellValue();
				boolean b = checkMajor(majorName);
				if (b) {
					Major major = new Major();

					// major.setMajorCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					major.setMajorName(StringUtil.checkString(row.getCell(1)
							.getStringCellValue()));
					major.setDisabled(false);
					majorDao.createMajor(major);

				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("majorName", majorName);
					Major m = majorDao.getMajorByMap(map);
					m.setDisabled(false);
					m.setMajorName(majorName);
					majorDao.updateMajor(m);

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
		if (!StringUtil.checkNull(ids)) {
			if (ids.contains(",")) {
				String[] idArray = ids.split(",");
				for (int i = 0; i < idArray.length; i++) {
					Major m = majorDao
							.getMajorById(Integer.valueOf(idArray[i]));
					m.setDisabled(true);
					majorDao.updateMajor(m);
					// majorDao.deleteMajorById(Integer.valueOf(idArray[i]));
				}
			} else {
				Major m = majorDao.getMajorById(Integer.valueOf(ids));
				m.setDisabled(true);
				majorDao.updateMajor(m);
				// majorDao.deleteMajorById(Integer.valueOf(ids));
			}
		}

	}

}
