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
import com.dao.PercentDao;
import com.entity.Percent;
import com.service.PercentService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class PercentServiceImpl implements PercentService {

	@Resource
	private PercentDao percentDao;

	public void createPercent(Percent percent) {
		percentDao.createPercent(percent);
	}

	public void deletePercentById(Integer id) {
		Percent c = percentDao.getPercentById(id);
		c.setDisabled(true);
		percentDao.updatePercent(c);
		// percentDao.deletePercentById(id);
	}

	public List<Percent> getPercentList() {
		return percentDao.getPercentList();
	}

	public void updatePercent(Percent percent) {
		percentDao.updatePercent(percent);
	}

	public Percent getPercentById(Integer id) {
		return percentDao.getPercentById(id);
	}

	public Percent getPercentByMap(Map<String, Object> map) {
		return percentDao.getPercentByMap(map);
	}

	public List<Percent> getPercentListByMap(Map<String, Object> map) {
		return percentDao.getPercentListByMap(map);
	}

	public List<Percent> getPercentListByHQL(String hql) {
		return percentDao.getPercentListByHQL(hql);
	}

	public PageBean getPercentPageByMap(Page page, Map<String, Object> map) {
		return percentDao.getPercentPageByMap(page, map);
	}

	public PageBean getPercentPageByHQL(Page page, String hql) {
		return percentDao.getPercentPageByHQL(page, hql);
	}

	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Percent> list = percentDao.getPercentListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "ID", "有效缴费人数最小值", "有效缴费人数最大值", "返款比例" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Percent c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getId());
				bodyRow.createCell(1).setCellValue(c.getMinNum());
				bodyRow.createCell(2).setCellValue(c.getMaxNum());
				bodyRow.createCell(3).setCellValue(c.getPercent());

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
	 * @param percentCode
	 * @return
	 */
	private boolean checkPercent(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return StringUtil.checkNull(percentDao.getPercentByMap(map));
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

				int id = (int) row.getCell(0).getNumericCellValue();
				boolean b = checkPercent(id);
				if (b) {
					Percent c = new Percent();
					c.setMinNum((int) row.getCell(1).getNumericCellValue());
					c.setMaxNum((int) row.getCell(2).getNumericCellValue());
					c.setPercent(row.getCell(3).getNumericCellValue());
					percentDao.createPercent(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					Percent c = percentDao.getPercentByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					c.setMinNum((int) row.getCell(1).getNumericCellValue());
					c.setMaxNum((int) row.getCell(2).getNumericCellValue());
					c.setPercent(row.getCell(3).getNumericCellValue());
					percentDao.updatePercent(c);
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
					Percent c = percentDao.getPercentById(Integer
							.valueOf(idArray[i]));
					c.setDisabled(true);
					percentDao.updatePercent(c);
					// percentDao.deletePercentById(Integer.valueOf(idArray[i]));
				}
			} else {
				Percent c = percentDao.getPercentById(Integer.valueOf(ids));
				c.setDisabled(true);
				percentDao.updatePercent(c);
				// percentDao.deletePercentById(Integer.valueOf(ids));
			}
		}

	}

}
