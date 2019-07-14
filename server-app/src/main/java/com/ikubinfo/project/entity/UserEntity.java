package com.ikubinfo.project.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "perdorues")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id", nullable = false, unique = true, length = 11)
	private int id;
	@Column (name="avatar")
	private String avatar;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;
	@Column(name = "birthdate")
	private Date birthdate;
	@Column(name = "email")
	private String email;
	@Column(name = "address")
	private String address;
	@Column(name = "flag")
	private boolean flag;

	public UserEntity() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", avatar=" + avatar + ", username=" + username + ", password=" + password
				+ ", role=" + role + ", birthdate=" + birthdate + ", email=" + email + ", address=" + address
				+ ", flag=" + flag + "]";
	}

	

}