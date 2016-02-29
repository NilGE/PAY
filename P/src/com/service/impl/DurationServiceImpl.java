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
import com.dao.DurationDao;
import com.entity.Duration;
import com.service.DurationService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class DurationServiceImpl implements DurationService {

	@Resource
	private DurationDao durationDao;


	public void createDuration(Duration duration) {
		durationDao.createDuration(duration);
	}

	public void deleteDurationById(Integer id) {
		Duration c = durationDao.getDurationById(id);
		c.setDisabled(true);
		durationDao.updateDuration(c);
		// durationDao.deleteDurationById(id);
	}

	public List<Duration> getDurationList() {
		return durationDao.getDurationList();
	}

	public void updateDuration(Duration duration) {
		durationDao.updateDuration(duration);
	}

	public Duration getDurationById(Integer id) {
		return durationDao.getDurationById(id);
	}
	
	public Duration getDurationByMap(Map<String, Object> map) {	
		return durationDao.getDurationByMap(map);
	}

	public List<Duration> getDurationListByMap(Map<String, Object> map) {
		return durationDao.getDurationListByMap(map);
	}
	
	public List<Duration> getDurationListByHQL(String hql) {
		return durationDao.getDurationListByHQL(hql);
	}

	public PageBean getDurationPageByMap(Page page, Map<String, Object> map) {
		return durationDao.getDurationPageByMap(page, map);
	}

	public PageBean getDurationPageByHQL(Page page, String hql) {
		return durationDao.getDurationPageByHQL(page, hql);
	}
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Duration> list = durationDao.getDurationListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "工作名称", "工作内容","工作名称", "工作内容","是否生效" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Duration c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getName());
				bodyRow.createCell(1).setCellValue(c.getContent());
				bodyRow.createCell(2).setCellValue(c.getStartDate());
				bodyRow.createCell(3).setCellValue(c.getEndDate());
				bodyRow.createCell(4).setCellValue(c.isValid());

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
	 * @param durationCode
	 * @return
	 */
	private boolean checkDuration(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		return StringUtil.checkNull(durationDao.getDurationByMap(map));
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

				String d_name = row.getCell(0).getStringCellValue();
				boolean b = checkDuration(d_name);
				if (b) {
					Duration c = new Duration(); 
					c.setName(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					c.setContent(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					c.setStartDate(StringUtil.checkString(row.getCell(2).getStringCellValue()));
					c.setEndDate(StringUtil.checkString(row.getCell(3).getStringCellValue()));
					c.setValid(row.getCell(4).getBooleanCellValue());
					durationDao.createDuration(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", d_name);
					Duration c = durationDao.getDurationByMap(map);
					// 以新导入数据为准
					c.setName(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					c.setContent(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					c.setStartDate(StringUtil.checkString(row.getCell(2).getStringCellValue()));
					c.setEndDate(StringUtil.checkString(row.getCell(3).getStringCellValue()));
					c.setValid(row.getCell(4).getBooleanCellValue());
					c.setDisabled(false);
					durationDao.updateDuration(c);
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
					Duration c = durationDao.getDurationById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					durationDao.updateDuration(c);
					// durationDao.deleteDurationById(Integer.valueOf(idArray[i]));
				}
			} else {
				Duration c = durationDao.getDurationById(Integer.valueOf(ids));
				c.setDisabled(true);
				durationDao.updateDuration(c);
				// durationDao.deleteDurationById(Integer.valueOf(ids));
			}
		}

	}

}
