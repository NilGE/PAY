package com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.collections4.map.HashedMap;
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
import com.dao.GroupDao;
import com.dao.UserDao;
import com.entity.Campus;
import com.entity.Group;
import com.entity.User;
import com.service.UserService;
import com.util.ProjectConfig;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-07 11:08:44
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private CampusDao campusDao;
	
	@Resource
	private GroupDao groupDao;

	public void createUser(User user) {
		user.setPassword(StringUtil.md5(user.getPassword()));
		userDao.createUser(user);
	}

	public void deleteUserById(Integer id) {
		User c = userDao.getUserById(id);
		c.setDisabled(true);
		userDao.updateUser(c);
		// userDao.deleteUserById(id);
	}

	public List<User> getUserList() {
		return userDao.getUserList();
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public User getUserById(Integer id) {
		return userDao.getUserById(id);
	}

	public User getUserByMap(Map<String, Object> map) {
		return userDao.getUserByMap(map);
	}

	public List<User> getUserListByMap(Map<String, Object> map) {
		return userDao.getUserListByMap(map);
	}

	public List<User> getUserListByHQL(String hql) {
		return userDao.getUserListByHQL(hql);
	}

	public PageBean getUserPageByMap(Page page, Map<String, Object> map) {
		return userDao.getUserPageByMap(page, map);
	}

	public PageBean getUserPageByHQL(Page page, String hql) {
		return userDao.getUserPageByHQL(page, hql);
	}


	public boolean checkRegister(String username) {
		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("username", username);
		List<User> result = userDao.getUserListByMap(map);
		return null == result || result.size() == 0;
	}

	public boolean checkInit() {
		List<Group> result = groupDao.getGroupList();
		return null == result || result.size() == 0;
	}

	@Override
	public void init(String username, String password) {
		Group group1 = new Group();
		Group group2 = new Group();

		group1.setGroupName("管理员");
		group2.setGroupName("财务人员");

		group1.setGroupCode("ADMIN");
		group2.setGroupCode("PAY");

		groupDao.createGroup(group1);
		groupDao.createGroup(group2);

		// 初始化管理员，账户密码都为1
		User user = new User();
		user.setUsername(username);
		user.setPassword(StringUtil.md5(password));
		user.setGroup(group1);
		userDao.createUser(user);
		
	}

	@Override
	public boolean checkLogin(String username, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		User user = userDao.getUserByMap(map);
		if (!StringUtil.checkNull(user)) {
			if (StringUtil.md5(password).equals(user.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("disabled", false);
		List<User> list = userDao.getUserListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出excel例子");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "用户名", "权限", "校区" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				User c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getUsername());
				bodyRow.createCell(1).setCellValue(c.getGroup().getGroupName());
				bodyRow.createCell(2).setCellValue(c.getCampus().getCampusName());

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
	 * @param userCode
	 * @return
	 */
	private boolean checkUser(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		return StringUtil.checkNull(userDao.getUserByMap(map));
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

				String username = row.getCell(0).getStringCellValue();
				boolean b = checkUser(username);
				if (b) {
					User c = new User();
					String groupName = StringUtil.checkString(row.getCell(1).getStringCellValue());
					String campusName = StringUtil.checkString(row.getCell(2).getStringCellValue());
					c.setUsername(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("groupName", groupName);
					Group g = groupDao.getGroupByMap(map);
					map.clear();
					map.put("campusName", campusName);
					Campus ca = campusDao.getCampusByMap(map);
					c.setCampus(ca);
					c.setGroup(g);
					c.setPassword(StringUtil.md5(ProjectConfig.DEFAULT_PASSWORD));
					userDao.createUser(c);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("username", username);
					User c = userDao.getUserByMap(map);
					String groupName = StringUtil.checkString(row.getCell(1).getStringCellValue());
					String campusName = StringUtil.checkString(row.getCell(2).getStringCellValue());
					// 以新导入数据为准
					c.setDisabled(false);
					c.setUsername(StringUtil.checkString(row.getCell(0).getStringCellValue()));
					map.clear();
					map.put("groupName", groupName);
					Group g = groupDao.getGroupByMap(map);
					map.clear();
					map.put("campusName", campusName);
					Campus ca = campusDao.getCampusByMap(map);
					c.setCampus(ca);
					c.setGroup(g);
					c.setPassword(StringUtil.md5(ProjectConfig.DEFAULT_PASSWORD));
					userDao.updateUser(c);
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
					User c = userDao.getUserById(Integer.valueOf(idArray[i]));
					c.setDisabled(true);
					userDao.updateUser(c);
					// userDao.deleteUserById(Integer.valueOf(idArray[i]));
				}
			} else {
				User c = userDao.getUserById(Integer.valueOf(ids));
				c.setDisabled(true);
				userDao.updateUser(c);
				// userDao.deleteUserById(Integer.valueOf(ids));
			}
		}

	}


}
