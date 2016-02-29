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
import com.dao.LevelDao;
import com.entity.Level;
import com.service.LevelService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class LevelServiceImpl implements LevelService {

	@Resource
	private LevelDao levelDao;


	public void createLevel(Level level) {
		levelDao.createLevel(level);
	}

	public void deleteLevelById(Integer id) {
		Level c = levelDao.getLevelById(id);
		c.setDisabled(true);
		levelDao.updateLevel(c);
		// levelDao.deleteLevelById(id);
	}

	public List<Level> getLevelList() {
		return levelDao.getLevelList();
	}

	public void updateLevel(Level level) {
		levelDao.updateLevel(level);
	}

	public Level getLevelById(Integer id) {
		return levelDao.getLevelById(id);
	}
	
	public Level getLevelByMap(Map<String, Object> map) {	
		return levelDao.getLevelByMap(map);
	}

	public List<Level> getLevelListByMap(Map<String, Object> map) {
		return levelDao.getLevelListByMap(map);
	}
	
	public List<Level> getLevelListByHQL(String hql) {
		return levelDao.getLevelListByHQL(hql);
	}

	public PageBean getLevelPageByMap(Page page, Map<String, Object> map) {
		return levelDao.getLevelPageByMap(page, map);
	}

	public PageBean getLevelPageByHQL(Page page, String hql) {
		return levelDao.getLevelPageByHQL(page, hql);
	}
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Level> list = levelDao.getLevelListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "层次代码", "层次名称" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Level c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getLevelCode());
				bodyRow.createCell(1).setCellValue(c.getLevelName());

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
	 * @param levelCode
	 * @return
	 */
	private boolean checkLevel(String levelCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("levelCode", levelCode);
		return StringUtil.checkNull(levelDao.getLevelByMap(map));
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

				String levelCode = row.getCell(0).getStringCellValue();
				boolean b = checkLevel(levelCode);
				if (b) {
					Level c = new Level();
					c.setLevelCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					c.setLevelName(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					levelDao.createLevel(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("levelCode", levelCode);
					Level c = levelDao.getLevelByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					c.setLevelCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					c.setLevelName(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					levelDao.updateLevel(c);
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
					Level c = levelDao.getLevelById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					levelDao.updateLevel(c);
					// levelDao.deleteLevelById(Integer.valueOf(idArray[i]));
				}
			} else {
				Level c = levelDao.getLevelById(Integer.valueOf(ids));
				c.setDisabled(true);
				levelDao.updateLevel(c);
				// levelDao.deleteLevelById(Integer.valueOf(ids));
			}
		}

	}

}
