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
 * @entityName 结算批次与学分标准
 * @author neuhxy@163.com
 * @version 2015-2-12 13:00:55
 */

@Entity
@Table(name = "t_standard")
public class Standard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private Integer id; // ID

	@Column(length = 11)
	private int credit; // 需要学分数 例如：38\42

	@Column(length = 32)
	private String termContent; // 第几学期 例如：第一学期
	
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

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getTermContent() {
		return termContent;
	}

	public void setTermContent(String termContent) {
		this.termContent = termContent;
	}

}
