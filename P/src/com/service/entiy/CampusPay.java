package com.service.entiy;

import java.util.List;
import java.util.Set;

import com.entity.Campus;
import com.entity.Level;
import com.entity.Major;
import com.entity.Record;
import com.entity.Term;

/**
 * @author neuhxy
 *
 */
public class CampusPay {

	private Integer id; // ID

	private Campus campus; // 缴费分析对应的学习中心

	private int total; // 缴费总人数

	private int valid; // 有效缴费人数

	private double percent; // 返款比例

	private double totalMoney; // 总金额

	private double backMoney; // 应返款金额

	private int dynamicNumber; // 参与动态分成比例的人数

	private double dynamicMoney; // 动态分成比例的金额

	private int staticNumber; // 参与固定分成人数；

	private double staticBackMoney; // 固定分成金额
	
	private double dynamicBackMoney; // 动态分成金额
	
	private double staticMoney; // 固定缴费金额

	private double staticPercent; // 执行固定分成的比例

	private Set<String> staticTerm; // 参与固定分成比例的批次

	private Set<String> dynamicTerm; // 参与动态分成比例的批次

	private String termName; // 入学批次

	private String duration; // 缴费周期

	private String info; // 计算过程

	private Set<Major> majors; // 所包含专业

	private Set<Level> levels; // 所包含层次

	private Set<Term> terms; // 所包含批次

	private List<Record> records; // 该中心的所有缴费记录

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public double getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(double backMoney) {
		this.backMoney = backMoney;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Set<Major> getMajors() {
		return majors;
	}

	public void setMajors(Set<Major> majors) {
		this.majors = majors;
	}

	public Set<Level> getLevels() {
		return levels;
	}

	public void setLevels(Set<Level> levels) {
		this.levels = levels;
	}

	public Set<Term> getTerms() {
		return terms;
	}

	public void setTerms(Set<Term> terms) {
		this.terms = terms;
	}

	public int getDynamicNumber() {
		return dynamicNumber;
	}

	public void setDynamicNumber(int dynamicNumber) {
		this.dynamicNumber = dynamicNumber;
	}

	public double getDynamicMoney() {
		return dynamicMoney;
	}

	public void setDynamicMoney(double dynamicMoney) {
		this.dynamicMoney = dynamicMoney;
	}

	public int getStaticNumber() {
		return staticNumber;
	}

	public void setStaticNumber(int staticNumber) {
		this.staticNumber = staticNumber;
	}

	public double getStaticBackMoney() {
		return staticBackMoney;
	}

	public void setStaticBackMoney(double staticBackMoney) {
		this.staticBackMoney = staticBackMoney;
	}

	public double getStaticPercent() {
		return staticPercent;
	}

	public void setStaticPercent(double staticPercent) {
		this.staticPercent = staticPercent;
	}

	public Set<String> getStaticTerm() {
		return staticTerm;
	}

	public void setStaticTerm(Set<String> staticTerm) {
		this.staticTerm = staticTerm;
	}

	public Set<String> getDynamicTerm() {
		return dynamicTerm;
	}

	public void setDynamicTerm(Set<String> dynamicTerm) {
		this.dynamicTerm = dynamicTerm;
	}

	public double getDynamicBackMoney() {
		return dynamicBackMoney;
	}

	public void setDynamicBackMoney(double dynamicBackMoney) {
		this.dynamicBackMoney = dynamicBackMoney;
	}

	public double getStaticMoney() {
		return staticMoney;
	}

	public void setStaticMoney(double staticMoney) {
		this.staticMoney = staticMoney;
	}

}
