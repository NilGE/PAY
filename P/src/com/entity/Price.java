package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 实体类
 * 
 * @entityName 学分单价
 * @author neuhxy@163.com
 * @version 2015-2-12 13:00:55
 */

@Entity
@Table(name = "t_price")
public class Price {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private Integer id; // ID

	@ManyToOne
	@JoinColumn(name = "campus_id")
	private Campus campus; // 学习中心

	@ManyToOne
	@JoinColumn(name = "major_id")
	private Major major; // 专业

	@ManyToOne
	@JoinColumn(name = "level_id")
	private Level level; // 入学层次

	@ManyToOne
	@JoinColumn(name = "term_id")
	private Term term; // 入学批次

	@Column
	private double price; // 学分单价
	
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

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
