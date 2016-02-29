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
import com.dao.CampusDao;
import com.entity.Campus;
import com.service.CampusService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Service
public class CampusServiceImpl implements CampusService {

	@Resource
	private CampusDao campusDao;

	public void createCampus(Campus campus) {
		campusDao.createCampus(campus);
	}

	public void deleteCampusById(Integer id) {
		Campus c = campusDao.getCampusById(id);
		c.setDisabled(true);
		campusDao.updateCampus(c);
		// campusDao.deleteCampusById(id);
	}

	public List<Campus> getCampusList() {
		return campusDao.getCampusList();
	}

	public void updateCampus(Campus campus) {
		campusDao.updateCampus(campus);
	}

	public Campus getCampusById(Integer id) {
		return campusDao.getCampusById(id);
	}

	public Campus getCampusByMap(Map<String, Object> map) {
		return campusDao.getCampusByMap(map);
	}

	public List<Campus> getCampusListByMap(Map<String, Object> map) {
		return campusDao.getCampusListByMap(map);
	}

	public List<Campus> getCampusListByHQL(String hql) {
		return campusDao.getCampusListByHQL(hql);
	}

	public PageBean getCampusPageByMap(Page page, Map<String, Object> map) {
		return campusDao.getCampusPageByMap(page, map);
	}

	public PageBean getCampusPageByHQL(Page page, String hql) {
		return campusDao.getCampusPageByHQL(page, hql);
	}

	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Campus> list = campusDao.getCampusListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "学习中心代码", "学习中心名称" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Campus c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getCampusCode());
				bodyRow.createCell(1).setCellValue(c.getCampusName());

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
	 * 检测学习中心是否存在
	 * 
	 * @param campusCode
	 * @return
	 */
	private boolean checkCampus(String campusCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campusCode", campusCode);
		return StringUtil.checkNull(campusDao.getCampusByMap(map));
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

				String campusCode = row.getCell(0).getStringCellValue();
				boolean b = checkCampus(campusCode);
				if (b) {
					Campus c = new Campus();
					c.setCampusCode(StringUtil.checkString(row.getCell(0)
							.getStringCellValue()));
					c.setCampusName(StringUtil.checkString(row.getCell(1)
							.getStringCellValue()));
					campusDao.createCampus(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("campusCode", campusCode);
					Campus c = campusDao.getCampusByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					c.setCampusCode(StringUtil.checkString(row.getCell(0)
							.getStringCellValue()));
					c.setCampusName(StringUtil.checkString(row.getCell(1)
							.getStringCellValue()));
					campusDao.updateCampus(c);
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
					Campus c = campusDao.getCampusById(Integer
							.valueOf(idArray[i]));
					c.setDisabled(true);
					campusDao.updateCampus(c);
					// campusDao.deleteCampusById(Integer.valueOf(idArray[i]));
				}
			} else {
				Campus c = campusDao.getCampusById(Integer.valueOf(ids));
				c.setDisabled(true);
				campusDao.updateCampus(c);
				// campusDao.deleteCampusById(Integer.valueOf(ids));
			}
		}

	}

}
