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
import com.dao.GroupDao;
import com.entity.Group;
import com.service.GroupService;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-24 15:43:32
 */
@Service
public class GroupServiceImpl implements GroupService {

	@Resource
	private GroupDao groupDao;


	public void createGroup(Group group) {
		groupDao.createGroup(group);
	}

	public void deleteGroupById(Integer id) {
		Group c = groupDao.getGroupById(id);
		c.setDisabled(true);
		groupDao.updateGroup(c);
		// groupDao.deleteGroupById(id);
	}

	public List<Group> getGroupList() {
		return groupDao.getGroupList();
	}

	public void updateGroup(Group group) {
		groupDao.updateGroup(group);
	}

	public Group getGroupById(Integer id) {
		return groupDao.getGroupById(id);
	}
	
	public Group getGroupByMap(Map<String, Object> map) {	
		return groupDao.getGroupByMap(map);
	}

	public List<Group> getGroupListByMap(Map<String, Object> map) {
		return groupDao.getGroupListByMap(map);
	}
	
	public List<Group> getGroupListByHQL(String hql) {
		return groupDao.getGroupListByHQL(hql);
	}

	public PageBean getGroupPageByMap(Page page, Map<String, Object> map) {
		return groupDao.getGroupPageByMap(page, map);
	}

	public PageBean getGroupPageByHQL(Page page, String hql) {
		return groupDao.getGroupPageByHQL(page, hql);
	}
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<Group> list = groupDao.getGroupListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "名称", "代码" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Group c = list.get(i);

				// bodyRow.createCell(0).setCellValue(c.getGroupCode());
				// bodyRow.createCell(1).setCellValue(c.getGroupName());

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
	 * @param groupCode
	 * @return
	 */
	private boolean checkGroup(String groupCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupCode", groupCode);
		return StringUtil.checkNull(groupDao.getGroupByMap(map));
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

				String groupCode = row.getCell(0).getStringCellValue();
				boolean b = checkGroup(groupCode);
				if (b) {
					Group c = new Group();
					// c.setGroupCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					// c.setGroupName(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					groupDao.createGroup(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("groupCode", groupCode);
					Group c = groupDao.getGroupByMap(map);
					// 以新导入数据为准
					c.setDisabled(false);
					// c.setGroupCode(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					// c.setGroupName(StringUtil.checkString(row.getCell(1).getStringCellValue()));
					groupDao.updateGroup(c);
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
					Group c = groupDao.getGroupById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					groupDao.updateGroup(c);
					// groupDao.deleteGroupById(Integer.valueOf(idArray[i]));
				}
			} else {
				Group c = groupDao.getGroupById(Integer.valueOf(ids));
				c.setDisabled(true);
				groupDao.updateGroup(c);
				// groupDao.deleteGroupById(Integer.valueOf(ids));
			}
		}

	}

}
