package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实体类
 * 
 * @entityName 记录
 * @author neuhxy@163.com
 * @version 2015-2-12 12:48:36
 */

@Entity
@Table(name = "t_record")
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private Integer id; // ID
	
	@Column(length = 32)
	private String payid; // 缴费序号
	
	@Column(length = 32)
	private String paydate; // 缴费日期

	
	@Column(length = 32)
	private String regNO; // 报名编号


	@Column(length = 32)
	private String aoPengNO; // 奥鹏卡号

	@Column(length = 32)
	private String college; // 院校

	@Column(length = 32)
	private String stuid; // 院校学号

	@Column(length = 32)
	private String manageCenter; // 管理中心

	@Column(length = 32)
	private String campusName; // 学习中心

	@Column(length = 32)
	private String campusCode; // 批次代码

	@Column(length = 32)
	private String levelName; // 入学批次

	@Column(length = 32)
	private String termName; // 层次

	@Column(length = 32)
	private String majorName; // 专业名称

	@Column(length = 32)
	private String name; // 姓名

	@Column(length = 32)
	private String identity; // 身份证号码

	@Column(length = 32)
	private String item; // 费用科目

	@Column(length = 32)
	private String money; // 金额

	@Column(length = 32)
	private String way; // 费用来源
	
	private boolean disabled; // 是否失效

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAoPengNO() {
		return aoPengNO;
	}

	public void setAoPengNO(String aoPengNO) {
		this.aoPengNO = aoPengNO;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getManageCenter() {
		return manageCenter;
	}

	public void setManageCenter(String manageCenter) {
		this.manageCenter = manageCenter;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getCampusCode() {
		return campusCode;
	}

	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getRegNO() {
		return regNO;
	}

	public void setRegNO(String regNO) {
		this.regNO = regNO;
	}

}
