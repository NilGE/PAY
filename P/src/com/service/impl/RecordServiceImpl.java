package com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import jxl.biff.StringHelper;

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

import com.common.tool.util.DateUtil;
import com.common.tool.util.Page;
import com.common.tool.util.PageBean;
import com.common.tool.util.StringUtil;
import com.dao.CampusDao;
import com.dao.DurationDao;
import com.dao.LevelDao;
import com.dao.MajorDao;
import com.dao.PercentDao;
import com.dao.PriceDao;
import com.dao.RecordDao;
import com.dao.StandardDao;
import com.dao.TermDao;
import com.entity.Campus;
import com.entity.Duration;
import com.entity.Percent;
import com.entity.Price;
import com.entity.Record;
import com.entity.Term;
import com.entity.User;
import com.service.RecordService;
import com.service.entiy.CampusPay;

/**
 * Service层实现
 * 
 * @author admin
 * @version 2015-04-07 16:54:35
 */
@Service
public class RecordServiceImpl implements RecordService {

	@Resource
	private RecordDao recordDao;

	@Resource
	private CampusDao campusDao;

	@Resource
	private TermDao termDao;

	@Resource
	private MajorDao majorDao;

	@Resource
	private PercentDao percentDao;

	@Resource
	private LevelDao levelDao;

	@Resource
	private PriceDao priceDao;

	@Resource
	private DurationDao durationDao;

	@Resource
	private StandardDao standardDao;

	public void createRecord(Record record) {
		recordDao.createRecord(record);
	}

	public void deleteRecordById(Integer id) {
		Record r = recordDao.getRecordById(id);
		r.setDisabled(true);
		recordDao.updateRecord(r);
		// recordDao.deleteRecordById(id);
	}

	public List<Record> getRecordList() {
		return recordDao.getRecordList();
	}

	public void updateRecord(Record record) {
		recordDao.updateRecord(record);
	}

	public Record getRecordById(Integer id) {
		return recordDao.getRecordById(id);
	}

	public Record getRecordByMap(Map<String, Object> map) {
		return recordDao.getRecordByMap(map);
	}

	public List<Record> getRecordListByMap(Map<String, Object> map) {
		return recordDao.getRecordListByMap(map);
	}

	public List<Record> getRecordListByHQL(String hql) {
		return recordDao.getRecordListByHQL(hql);
	}

	public PageBean getRecordPageByMap(Page page, Map<String, Object> map) {
		return recordDao.getRecordPageByMap(page, map);
	}

	public PageBean getRecordPageByHQL(Page page, String hql) {
		return recordDao.getRecordPageByHQL(page, hql);
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
				String payid = row.getCell(0).getStringCellValue();
				String identity = row.getCell(13).getStringCellValue();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("payid", payid);
				map1.put("identity", identity);
				boolean b = checkRecord(map1);
				if (b) {
					System.out.println(i + " new ");
					Record record = new Record();

					record.setPayid(row.getCell(0).getStringCellValue());
					record.setPaydate(row.getCell(1).getStringCellValue());
					record.setRegNO(row.getCell(2).getStringCellValue());
					record.setAoPengNO(row.getCell(3).getStringCellValue());
					record.setCollege(row.getCell(4).getStringCellValue());
					record.setStuid(row.getCell(5).getStringCellValue());

					record.setManageCenter(row.getCell(6).getStringCellValue());
					record.setCampusName(row.getCell(7).getStringCellValue());
					record.setCampusCode(row.getCell(8).getStringCellValue());
					record.setTermName(row.getCell(9).getStringCellValue());
					record.setLevelName(row.getCell(10).getStringCellValue());
					record.setMajorName(row.getCell(11).getStringCellValue());
					record.setName(row.getCell(12).getStringCellValue());
					record.setIdentity(row.getCell(13).getStringCellValue());

					record.setItem(row.getCell(14).getStringCellValue());
					record.setMoney(row.getCell(15).getStringCellValue());
					record.setWay(row.getCell(16).getStringCellValue());
					record.setDisabled(false);
					recordDao.createRecord(record);

				} else {
					System.out.println(i + " update ");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("payid", payid);
					map.put("identity", identity);
					Record record = recordDao.getRecordByMap(map);
					record.setPaydate(row.getCell(1).getStringCellValue());
					record.setRegNO(row.getCell(2).getStringCellValue());
					record.setAoPengNO(row.getCell(3).getStringCellValue());
					record.setCollege(row.getCell(4).getStringCellValue());
					record.setStuid(row.getCell(5).getStringCellValue());

					record.setManageCenter(row.getCell(6).getStringCellValue());
					record.setCampusName(row.getCell(7).getStringCellValue());
					record.setCampusCode(row.getCell(8).getStringCellValue());
					record.setTermName(row.getCell(9).getStringCellValue());
					record.setLevelName(row.getCell(10).getStringCellValue());
					record.setMajorName(row.getCell(11).getStringCellValue());
					record.setName(row.getCell(12).getStringCellValue());
					record.setIdentity(row.getCell(13).getStringCellValue());

					record.setItem(row.getCell(14).getStringCellValue());
					record.setMoney(row.getCell(15).getStringCellValue());
					record.setWay(row.getCell(16).getStringCellValue());
					record.setDisabled(false);
					recordDao.updateRecord(record);
				}
			}
			System.out.println("数据导入成功！");
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
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

	/**
	 * 检测入学批次是否存在
	 * 
	 * @param termName
	 * @return
	 */
	private boolean checkTerm(String termName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termName", termName);
		return StringUtil.checkNull(termDao.getTermByMap(map));
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

	/**
	 * 检测记录是否已经存在
	 * 
	 * @param payid
	 * @return
	 */
	public boolean checkRecord(Map<String, Object> map) {
		return StringUtil.checkNull(recordDao.getRecordByMap(map));
	}

	@Override
	public void exportExcel(User u, ServletOutputStream outputStream) {
		String groupCode = u.getGroup().getGroupCode();
		String campusCode = null;
		if ("PAY".equals(groupCode)) {
			campusCode = u.getCampus().getCampusCode();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtil.checkNull(campusCode)) {
			map.put("campusCode", campusCode);
		}

		map.put("disabled", false);
		List<Record> records = recordDao.getRecordListByMap(map);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出全部缴费记录");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "缴费序号", "缴费日期", "报名编号", "奥鹏卡号", "院校", "院校学号",
				"管理中心", "学习中心", "学习中心代码", "入学批次", "层次", "专业", "姓名", "身份证号码",
				"缴费科目", "金额", "费用来源" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(records) && records.size() > 0) {
			for (int i = 0; i < records.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				Record record = records.get(i);

				bodyRow.createCell(0).setCellValue(record.getPayid());
				bodyRow.createCell(1).setCellValue(record.getPaydate());
				bodyRow.createCell(2).setCellValue(record.getRegNO());
				bodyRow.createCell(3).setCellValue(record.getAoPengNO());
				bodyRow.createCell(4).setCellValue(record.getCollege());
				bodyRow.createCell(5).setCellValue(record.getStuid());
				bodyRow.createCell(6).setCellValue(record.getMajorName());
				bodyRow.createCell(7).setCellValue(record.getCampusName());
				bodyRow.createCell(8).setCellValue(record.getCampusCode());
				bodyRow.createCell(9).setCellValue(record.getTermName());
				bodyRow.createCell(10).setCellValue(record.getLevelName());
				bodyRow.createCell(11).setCellValue(record.getMajorName());
				bodyRow.createCell(12).setCellValue(record.getName());
				bodyRow.createCell(13).setCellValue(record.getIdentity());
				bodyRow.createCell(14).setCellValue(record.getItem());
				bodyRow.createCell(15).setCellValue(record.getMoney());
				bodyRow.createCell(16).setCellValue(record.getWay());

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

	@Override
	public void exportBackMoney(User u, ServletOutputStream outputStream) {

		String groupCode = u.getGroup().getGroupCode();
		Integer user_id = u.getId();
		Integer campus_id = null;
		if ("PAY".equals(groupCode)) {
			campus_id = u.getCampus().getId();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtil.checkNull(campus_id)) {
			map.put("campus_id", campus_id);
		}
		List<CampusPay> list = calculate(map);

		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出各学习中心返款金额");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "学习中心名称", "有效缴费人数", "缴费总人数", "总金额", "返款比例",
				"应返款金额", "计算过程日志记录" };
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				CampusPay c = list.get(i);

				bodyRow.createCell(0).setCellValue(
						c.getCampus().getCampusName());
				bodyRow.createCell(1).setCellValue(c.getValid());
				bodyRow.createCell(2).setCellValue(c.getTotal());
				bodyRow.createCell(3).setCellValue(c.getTotalMoney());
				bodyRow.createCell(4).setCellValue(c.getPercent());
				bodyRow.createCell(5).setCellValue(c.getBackMoney());
				bodyRow.createCell(6).setCellValue(c.getInfo());

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

	@Override
	public void exportFile(User u, ServletOutputStream outputStream) {

		String groupCode = u.getGroup().getGroupCode();
		Integer campus_id = null;
		if ("PAY".equals(groupCode)) {
			campus_id = u.getCampus().getId();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtil.checkNull(campus_id)) {
			map.put("campus_id", campus_id);
		}
		List<CampusPay> list = calculate(map);
		
		Map<String, Object> c_map = new HashMap<String, Object>();
		c_map.clear();
		c_map.put("disabled", false);
		c_map.put("valid", true);
		Duration du = durationDao.getDurationByMap(c_map);

		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("导出各学习中心返款金额");
		XSSFRow headRow = sheet.createRow(0);
		String[] titles = { "学习中心名称", "学习中心代码","有效缴费人数", "缴费总人数", "总金额", "包含固定批次", "固定分成人数","固定分成比例",
				"固定缴费金额","固定分成金额","动态分成批次", "动态分成比例", "动态分成人数","动态缴费金额","动态返款金额","应返款总金额","导出时间","缴费周期"};
		// 设置表头
		for (int i = 0; i < titles.length; i++) {
			headRow.createCell(i).setCellValue(titles[i]);
		}

		if (!StringUtil.checkNull(list) && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				XSSFRow bodyRow = sheet.createRow(i + 1);
				CampusPay c = list.get(i);

				bodyRow.createCell(0).setCellValue(c.getCampus().getCampusName());
				bodyRow.createCell(1).setCellValue(c.getCampus().getCampusCode());
				bodyRow.createCell(2).setCellValue(c.getValid());
				bodyRow.createCell(3).setCellValue(c.getTotal());
				bodyRow.createCell(4).setCellValue(c.getTotalMoney());
				bodyRow.createCell(5).setCellValue(c.getStaticTerm().toString().replace("[", "").replace("]", ""));
				bodyRow.createCell(6).setCellValue(c.getStaticNumber());
				bodyRow.createCell(7).setCellValue(c.getStaticPercent());
				bodyRow.createCell(8).setCellValue(c.getStaticMoney());
				bodyRow.createCell(9).setCellValue(c.getStaticBackMoney());
				bodyRow.createCell(10).setCellValue(c.getDynamicTerm().toString().replace("[", "").replace("]", ""));
				if (!c.getDynamicTerm().isEmpty()) {
					bodyRow.createCell(11).setCellValue(c.getPercent());					
				}
				bodyRow.createCell(12).setCellValue(c.getDynamicNumber());
				bodyRow.createCell(13).setCellValue(c.getDynamicMoney());
				bodyRow.createCell(14).setCellValue(c.getDynamicMoney()*c.getPercent());
				bodyRow.createCell(15).setCellValue(c.getBackMoney());
				bodyRow.createCell(16).setCellValue(DateUtil.getCurrentDatetime());
				bodyRow.createCell(17).setCellValue(du.getStartDate()+"~"+du.getEndDate());

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
	 * 处理一个用户分多次付款的情况，将多次缴费合并为一次缴费
	 * 
	 * @param map
	 * @return
	 */
	public List<Record> dealRecords(Map<String, Object> map) {
		List<Record> result = new ArrayList<Record>();
		String campusCode = (String) map.get("campusCode");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String hql_d = "From Record where disabled = 0 and campusCode = '"
				+ campusCode + "' and paydate >= '" + startDate
				+ "' and paydate <= '" + endDate + "'";
		System.out.println(hql_d);
		Set<String> identiySet = new HashSet<String>();
		List<Record> list = recordDao.getRecordListByHQL(hql_d);
		if (null != list && list.size() > 0) {
			for (Record r : list) {
				identiySet.add(r.getIdentity());
			}
			Map<String, Object> d_map = new HashMap<String, Object>();
			for (String id : identiySet) {
				d_map.clear();
				d_map.put("identity", id);

				String hql_d2 = "From Record where disabled = 0 and identity = '"
						+ id + "' and paydate >= '" + startDate
						+ "' and paydate <= '" + endDate + "'";
				
				List<Record> records = recordDao.getRecordListByHQL(hql_d2);
				Record re = records.get(0);
				if (records.size() > 1) {
					Double money = Double.valueOf(re.getMoney());
					for (int i = 1; i < records.size(); i++) {
						money += Double.valueOf(records.get(i).getMoney());
					}
					re.setMoney(money.toString());
				}
				result.add(re);

			}
		}

		return result;
	}

	@Override
	public List<CampusPay> calculate(Map<String, Object> map) {
		List<CampusPay> campusPayList = new ArrayList<CampusPay>();

		if (map.isEmpty()) {
			// 如果map为空，计算各个学习中心的数据
			Map<String, Object> c_map = new HashMap<String, Object>();
			c_map.put("disabled", false);
			List<Campus> campusList = campusDao.getCampusListByMap(c_map);
			for (Campus campus : campusList) {

				Set<String> dynamicTerm = new HashSet<String>();
				Set<String> staticTerm = new HashSet<String>();
				int dynamicNumber = 0;
				int staticNumber = 0;
				double staticPercent = 0.0d;
				double dynamicPercent = 0.0d;

				CampusPay campusPay = new CampusPay();
				String campusCode = campus.getCampusCode();
				// ======================计算过程日志记录======================= //
				StringBuffer sb = new StringBuffer();
				Set<Term> termSet = new HashSet<Term>();

				// 查找符开启的缴费周期记录
				c_map.clear();
				c_map.put("disabled", false);
				c_map.put("valid", true);
				Duration duration = durationDao.getDurationByMap(c_map);
				if (StringUtil.checkNull(duration)) {
					// 如果缴费周期不存在，就计算全部时间的数据
					duration = new Duration();
				}
				c_map.clear();
				c_map.put("campusCode", campusCode);
				c_map.put("startDate", duration.getStartDate());
				c_map.put("endDate", duration.getEndDate());

				sb.append("缴费周期：" + duration.getStartDate() + " ~ "
						+ duration.getEndDate() + "\r\n");
				List<Record> records = dealRecords(c_map);

				if (!StringUtil.checkNull(records)) {
					sb.append(campus.getCampusName() + " 合计：" + records.size()
							+ "\r\n");
					// 该学习中心 在缴费周期内 有效记录 的所有缴费人数
					int total = records.size();
					// 有效缴费人数
					int valid = 0;
					double totalMoney = 0.0d; //收费总金额
					double backMoney = 0.0d; //返款总金额
					double backDynamicMoney = 0.0d; //动态返款金额
					double backStaticMoney = 0.0d; //固定返款金额
					double dynamicMoney = 0.0d; //动态缴费金额
					double staticMoney = 0.0d; //固定缴费金额

					for (int i = 0; i < total; i++) {
						Record record = records.get(i);
						// 计算 该学习中心 在缴费周期内 总金额
						double money = Double.valueOf(record.getMoney());
						totalMoney += money;

						String termName = record.getTermName();
						c_map.clear();
						c_map.put("termName", termName);
						c_map.put("disabled", false);
						Term te = termDao.getTermByMap(c_map);

						// ========================添加批次记录=============================//
						termSet.add(te);
						// ========================添加批次记录=============================//
						if (te.isDynamic()) {
							// 如果该学期需要执行动态分成比例
							double standardMoney = getStandardMoney(record);
							sb.append(i + 1 + ". 批次：" + termName);
							sb.append("..."+record.getPaydate()+ "...." );
							sb.append(" 应缴金额：" + standardMoney);
							sb.append(" 实际金额：" + money);
							if (money >= standardMoney) {
								valid++;
								sb.append(" OK " + "\r\n");
							} else {
								sb.append(" ERROR " + "\r\n");
							}
							//backDynamicMoney += money;
							dynamicMoney += money;
							dynamicTerm.add(te.getTermName());
							dynamicNumber++;

						} else {
							sb.append(i + 1 + ". 批次：" + termName);
							sb.append("..."+record.getPaydate()+ "...." );
							sb.append(" 实际金额：" + money);
							sb.append(" 固定比例 " + te.getPercent() + "\r\n");
							// 如果该学期不执行动态分成比例
							staticMoney += money;
							backStaticMoney += money * te.getPercent();
							// sb.append("固定：" + money + " * " + te.getPercent()
							// + " = " + money * te.getPercent() + "\t\n" );
							staticTerm.add(te.getTermName());
							staticNumber++;
							staticPercent = te.getPercent();
						}

					}
					String hql = "From Percent where disabled = 0 and minNum <= "
							+ valid + " and maxNum >= " + valid;
					// System.out.println(hql);
					List<Percent> percents = percentDao.getPercentListByHQL(hql);
					if (!StringUtil.checkNull(percents) && percents.size() > 0) {
						// 如果能找到 对应的Percent记录
						Percent percent = percents.get(0);
						double percentage = percent.getPercent();
						dynamicPercent = percentage;
						// =======================有效缴费人数=============================//
						sb.append("有效人数：" + valid);
						sb.append(" 动态分成比例：" + percentage + "\r\n");
						// =======================有效缴费人数=============================//
						backDynamicMoney = dynamicMoney * percentage;
						backMoney = backDynamicMoney + backStaticMoney;

						// =======================添加分成比例构成=============================//
						sb.append("返款 = (动态：" + dynamicMoney + " * "
								+ percentage + " = " + backDynamicMoney + " )+ (固定：" + staticMoney
								+ " * "+staticPercent+" = "+backStaticMoney+") = " + backMoney + " \r\n");
						// ========================添加分成比例构成============================//

					}
					campusPay.setBackMoney(backMoney);
					
					campusPay.setStaticBackMoney(backStaticMoney);
					campusPay.setStaticMoney(staticMoney);
					campusPay.setStaticNumber(staticNumber);
					campusPay.setStaticPercent(staticPercent);
					campusPay.setStaticTerm(staticTerm);
					
					campusPay.setDynamicBackMoney(backDynamicMoney);
					campusPay.setDynamicMoney(dynamicMoney);
					campusPay.setPercent(dynamicPercent);
					campusPay.setDynamicNumber(dynamicNumber);
					campusPay.setDynamicTerm(dynamicTerm);

					campusPay.setTotalMoney(totalMoney);
					campusPay.setValid(valid);
					campusPay.setCampus(campus);
					campusPay.setTotal(total);
					campusPay.setRecords(records);
					campusPayList.add(campusPay);
					campusPay.setTerms(termSet);
					// ==========================================================//
					sb.append("总金额：" + totalMoney + "\r\n");
					sb.append("包含的批次：");
					for (Term t : termSet) {
						sb.append(t.getTermName() + " ");
					}
					campusPay.setInfo(sb.toString());
					// ==========================================================//
				}

			}
		} else {
			// 只计算一个学习中心的缴费结果
			Set<String> dynamicTerm = new HashSet<String>();
			Set<String> staticTerm = new HashSet<String>();
			int dynamicNumber = 0;
			int staticNumber = 0;
			double staticPercent = 0.0d;
			double dynamicPercent = 0.0d;
			
			Map<String, Object> c_map = new HashMap<String, Object>();
			// 只计算一个学习中心的缴费结果
			CampusPay campusPay = new CampusPay();
			int campus_id = (Integer) map.get("campus_id");
			Campus campus = campusDao.getCampusById(campus_id);
			String campusCode = campus.getCampusCode();
			// ======================计算过程日志记录======================= //
			StringBuffer sb = new StringBuffer();
			Set<Term> termSet = new HashSet<Term>();

			// 查找符开启的缴费周期记录
			c_map.clear();
			c_map.put("disabled", false);
			c_map.put("valid", true);
			Duration duration = durationDao.getDurationByMap(c_map);
			if (StringUtil.checkNull(duration)) {
				// 如果缴费周期不存在，就计算全部时间的数据
				duration = new Duration();
			}
			c_map.clear();
			c_map.put("campusCode", campusCode);
			c_map.put("startDate", duration.getStartDate());
			c_map.put("endDate", duration.getEndDate());

			sb.append("缴费周期：" + duration.getStartDate() + " ~ "
					+ duration.getEndDate() + "\r\n");
			List<Record> records = dealRecords(c_map);

			if (!StringUtil.checkNull(records)) {
				sb.append(campus.getCampusName() + " 合计：" + records.size()
						+ "\r\n");
				// 该学习中心 在缴费周期内 有效记录 的所有缴费人数
				int total = records.size();
				// 有效缴费人数
				int valid = 0;
				double totalMoney = 0.0d; //收费总金额
				double backMoney = 0.0d; //返款总金额
				double backDynamicMoney = 0.0d; //动态返款金额
				double backStaticMoney = 0.0d; //固定返款金额
				double dynamicMoney = 0.0d; //动态缴费金额
				double staticMoney = 0.0d; //固定缴费金额

				for (int i = 0; i < total; i++) {
					Record record = records.get(i);
					// 计算 该学习中心 在缴费周期内 总金额
					double money = Double.valueOf(record.getMoney());
					totalMoney += money;

					String termName = record.getTermName();
					c_map.clear();
					c_map.put("termName", termName);
					c_map.put("disabled", false);
					Term te = termDao.getTermByMap(c_map);

					// ========================添加批次记录=============================//
					termSet.add(te);
					// ========================添加批次记录=============================//
					if (te.isDynamic()) {
						// 如果该学期需要执行动态分成比例
						double standardMoney = getStandardMoney(record);
						sb.append(i + 1 + ". 批次：" + termName);
						sb.append("..."+record.getPaydate()+ "...." );
						sb.append(" 应缴金额：" + standardMoney);
						sb.append(" 实际金额：" + money);
						if (money >= standardMoney) {
							valid++;
							sb.append(" OK " + "\r\n");
						} else {
							sb.append(" ERROR " + "\r\n");
						}
						//backDynamicMoney += money;
						dynamicMoney += money;
						dynamicTerm.add(te.getTermName());
						dynamicNumber++;

					} else {
						sb.append(i + 1 + ". 批次：" + termName);
						sb.append(" 实际金额：" + money);
						sb.append("..."+record.getPaydate()+ "...." );
						sb.append(" 固定比例 " + te.getPercent() + "\r\n");
						// 如果该学期不执行动态分成比例
						staticMoney += money;
						backStaticMoney += money * te.getPercent();
						// sb.append("固定：" + money + " * " + te.getPercent()
						// + " = " + money * te.getPercent() + "\t\n" );
						staticTerm.add(te.getTermName());
						staticNumber++;
						staticPercent = te.getPercent();
					}

				}
				String hql = "From Percent where disabled = 0 and minNum <= "
						+ valid + " and maxNum >= " + valid;
				// System.out.println(hql);
				List<Percent> percents = percentDao.getPercentListByHQL(hql);
				if (!StringUtil.checkNull(percents) && percents.size() > 0) {
					// 如果能找到 对应的Percent记录
					Percent percent = percents.get(0);
					double percentage = percent.getPercent();
					dynamicPercent = percentage;
					// =======================有效缴费人数=============================//
					sb.append("有效人数：" + valid);
					sb.append(" 动态分成比例：" + percentage + "\r\n");
					// =======================有效缴费人数=============================//
					backDynamicMoney = dynamicMoney * percentage;
					backMoney = backDynamicMoney + backStaticMoney;

					// =======================添加分成比例构成=============================//
					sb.append("返款 = (动态：" + dynamicMoney + " * "
							+ percentage + " = " + backDynamicMoney + " )+ (固定：" + staticMoney
							+ " * "+staticPercent+" = "+backStaticMoney+") = " + backMoney + " \r\n");
					// ========================添加分成比例构成============================//

				}
				campusPay.setBackMoney(backMoney);
				
				campusPay.setStaticBackMoney(backStaticMoney);
				campusPay.setStaticMoney(staticMoney);
				campusPay.setStaticNumber(staticNumber);
				campusPay.setStaticPercent(staticPercent);
				campusPay.setStaticTerm(staticTerm);
				
				campusPay.setDynamicBackMoney(backDynamicMoney);
				campusPay.setDynamicMoney(dynamicMoney);
				campusPay.setPercent(dynamicPercent);
				campusPay.setDynamicNumber(dynamicNumber);
				campusPay.setDynamicTerm(dynamicTerm);

				campusPay.setTotalMoney(totalMoney);
				campusPay.setValid(valid);
				campusPay.setCampus(campus);
				campusPay.setTotal(total);
				campusPay.setRecords(records);
				campusPayList.add(campusPay);
				campusPay.setTerms(termSet);
				// ==========================================================//
				sb.append("总金额：" + totalMoney + "\r\n");
				sb.append("包含的批次：");
				for (Term t : termSet) {
					sb.append(t.getTermName() + " ");
				}
				campusPay.setInfo(sb.toString());
				// ==========================================================//
			}


		}
		return campusPayList;
	}

	/**
	 * 获取该学生的缴费总金额
	 * 
	 * @param record
	 * @return
	 */
	private double getStandardMoney(Record record) {
		double standMoney = 0.0d;
		double price = 60.0d;
		Map<String, Object> map = new HashMap<String, Object>();

		String campusName = record.getCampusName();
		// String campusCode = record.getCampusCode();
		String majorName = record.getMajorName();
		String levelName = record.getLevelName();
		String termName = record.getTermName();
		/*
		 * int term = Integer.valueOf(termName);
		 * 
		 * String currentDate = DateUtil.getCurrentTime("yyMM"); int now =
		 * Integer.valueOf(currentDate);
		 */

		map.clear();
		map.put("campusName", campusName);
		map.put("disabled", false);
		int campus_id = campusDao.getCampusByMap(map).getId();

		map.clear();
		map.put("majorName", majorName);
		map.put("disabled", false);
		int major_id = majorDao.getMajorByMap(map).getId();

		map.clear();
		map.put("levelName", levelName);
		map.put("disabled", false);
		int level_id = levelDao.getLevelByMap(map).getId();

		map.clear();
		map.put("termName", termName);
		map.put("disabled", false);
		Term te = termDao.getTermByMap(map);
		int term_id = te.getId();

		map.clear();
		map.put("campus.id", campus_id);
		map.put("major.id", major_id);
		map.put("level.id", level_id);
		map.put("term.id", term_id);
		map.put("disabled", false);

		Price p = priceDao.getPriceByMap(map);
		//System.out.println("是否执行动态分成比例：" + te.isDynamic());
		//System.out.println(p.getCampus().getCampusName() + p.getMajor().getMajorName() + p.getTerm().getTermName() + p.getLevel().getLevelName() + p.getPrice());
		if (!StringUtil.checkNull(p)) {
			price = p.getPrice();
		}

		standMoney = price * te.getStandard().getCredit();
		/*
		 * if (now - term <= 7 && now - term >= 0) {
		 * System.out.println("第一学期："); // 说明该条记录是第一学期，需要修满42学分 map.clear();
		 * map.put("disabled", false); map.put("termContent", "第一学期"); Standard
		 * std = standardDao.getStandardByMap(map); standMoney = price *
		 * std.getCredit();
		 * 
		 * } else { // 都不是第一学期，应该交38学分的钱 map.clear(); map.put("disabled",
		 * false); map.put("termContent", "其他学期"); Standard std =
		 * standardDao.getStandardByMap(map); standMoney = price *
		 * std.getCredit(); }
		 */

		return standMoney;
	}

	@Override
	public void deleteAll(String ids) {
		System.out.println(ids);
		if (!StringUtil.checkNull(ids)) {
			if (ids.contains(",")) {
				String[] idArray = ids.split(",");
				for (int i = 0; i < idArray.length; i++) {
					Record r = recordDao.getRecordById(Integer
							.valueOf(idArray[i]));
					r.setDisabled(true);
					recordDao.updateRecord(r);
					// recordDao.deleteRecordById(Integer.valueOf(idArray[i]));
				}
			} else {
				Record r = recordDao.getRecordById(Integer.valueOf(ids));
				r.setDisabled(true);
				recordDao.updateRecord(r);
				// recordDao.deleteRecordById(Integer.valueOf(ids));
			}
		}

	}

}
