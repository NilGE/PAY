package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学习中心实体类
 * 
 * @entityName 学习中心
 * @author neuhxy@163.com
 * @version 2015-2-12 12:48:36
 */

@Entity
@Table(name = "t_campus")
public class Campus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private Integer id; // ID

	@Column(length = 32)
	private String campusName; // 学习中心名称

	@Column(length = 32)
	private String campusCode; // 学习中心代码
	
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

}
