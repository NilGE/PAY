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
 * 用户实体类：用于登录，区分用户身份属性
 * 
 * @entityName 用户
 * @author neuhxy@163.com
 * @version 2015-2-12 12:56:02
 */
@Entity
@Table(name = "t_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private Integer id; // ID

	@Column(name = "username")
	private String username; // 用户名

	@Column(name = "password")
	private String password; // 密码

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group; // 权限组

	@ManyToOne
	@JoinColumn(name = "campus_id")
	private Campus campus; // 校区

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

}
