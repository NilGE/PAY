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
import com.dao.LevelDao;
import com.dao.MajorDao;
import com.dao.PriceDao;
import com.dao.TermDao;
import com.entity.Campus;
import com.entity.Level;
import com.entity.Major;
import com.entity.Price;
import com.entity.Term;
import com.service.PriceService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class PriceServiceImpl implements PriceService {

	@Resource
	private PriceDao priceDao;
	
	@Resource
	private CampusDao campusDao;
	
	@Resource
	private MajorDao majorDao;
	
	@Resource
	private LevelDao levelDao;
	
	@Resource
	private TermDao termDao;


	public void createPrice(Price price) {
		priceDao.createPrice(price);
	}

	public void deletePriceById(Integer id) {
		Price c = priceDao.getPriceById(id);
		c.setDisabled(true);
		priceDao.updatePrice(c);
		// priceDao.deletePriceById(id);
	}

	public List<Price> getPriceList() {
		return priceDao.getPriceList();
	}

	public void updatePrice(Price price) {
		priceDao.updatePrice(price);
	}

	public Price getPriceById(Integer id) {
		return priceDao.getPriceById(id);
	}
	
	public Price getPriceByMap(Map<String, Object> map) {	
		return priceDao.getPriceByMap(map);
	}

	public List<Price> getPriceListByMap(Map<String, Object> map) {
		return priceDao.getPriceListByMap(map);
	}
	
	public List<Price> getPriceListByHQL(String hql) {
		return priceDao.getPriceListByHQL(hql);
	}

	public PageBean getPricePageByMap(Page page, Map<String, Object> map) {
		return priceDao.getPricePageByMap(page, map);
	}

	public PageBean getPricePageByHQL(Page page, String hql) {
		return priceDao.getPricePageByHQL(page, hql);
	}
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Price> list = priceDao.getPriceListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "学习中心", "专业名称", "入学层次", "入学批次", "学分单价" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Price c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getCampus().getCampusName());
				bodyRow.createCell(1).setCellValue(c.getMajor().getMajorName());
				bodyRow.createCell(2).setCellValue(c.getLevel().getLevelName());
				bodyRow.createCell(3).setCellValue(c.getTerm().getTermName());
				bodyRow.createCell(4).setCellValue(c.getPrice());
				

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
	 * @param priceCode
	 * @param termName 
	 * @param levelName 
	 * @param majorName 
	 * @return
	 */
	private boolean checkPrice(String campusName, String majorName, String levelName, String termName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("campusName", campusName);
		Campus campus = campusDao.getCampusByMap(map);
		map.clear();
		map.put("majorName", majorName);
		Major major = majorDao.getMajorByMap(map);
		map.clear();
		map.put("levelName", levelName);
		Level level = levelDao.getLevelByMap(map);
		map.clear();
		map.put("termName", termName);
		Term term = termDao.getTermByMap(map);
		
		map.clear();
		map.put("campus.id", campus.getId());
		map.put("major.id", major.getId());
		map.put("level.id", level.getId());
		map.put("term.id", term.getId());
		return StringUtil.checkNull(priceDao.getPriceByMap(map));
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

				String campusName = row.getCell(0).getStringCellValue();
				String majorName = row.getCell(1).getStringCellValue();
				String levelName = row.getCell(2).getStringCellValue();
				String termName = row.getCell(3).getStringCellValue();
				
				boolean b = checkPrice(campusName,majorName,levelName,termName);
				if (b) {
					Price c = new Price();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("campusName", campusName);
					Campus campus = campusDao.getCampusByMap(map);
					map.clear();
					map.put("majorName", majorName);
					Major major = majorDao.getMajorByMap(map);
					map.clear();
					map.put("levelName", levelName);
					Level level = levelDao.getLevelByMap(map);
					map.clear();
					map.put("termName", termName);
					Term term = termDao.getTermByMap(map);
					c.setCampus(campus);
					c.setMajor(major);
					c.setLevel(level);
					c.setTerm(term);
					c.setPrice(row.getCell(4).getNumericCellValue());
					priceDao.createPrice(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("campusName", campusName);
					Campus campus = campusDao.getCampusByMap(map);
					map.clear();
					map.put("majorName", majorName);
					Major major = majorDao.getMajorByMap(map);
					map.clear();
					map.put("levelName", levelName);
					Level level = levelDao.getLevelByMap(map);
					map.clear();
					map.put("termName", termName);
					Term term = termDao.getTermByMap(map);
					
					map.clear();
					map.put("campus.id", campus.getId());
					map.put("major.id", major.getId());
					map.put("level.id", level.getId());
					map.put("term.id", term.getId());
					
					Price c = priceDao.getPriceByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					c.setCampus(campus);
					c.setMajor(major);
					c.setLevel(level);
					c.setTerm(term);
					c.setPrice(row.getCell(4).getNumericCellValue());
					priceDao.updatePrice(c);
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
					Price c = priceDao.getPriceById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					priceDao.updatePrice(c);
					// priceDao.deletePriceById(Integer.valueOf(idArray[i]));
				}
			} else {
				Price c = priceDao.getPriceById(Integer.valueOf(ids));
				c.setDisabled(true);
				priceDao.updatePrice(c);
				// priceDao.deletePriceById(Integer.valueOf(ids));
			}
		}

	}

}
