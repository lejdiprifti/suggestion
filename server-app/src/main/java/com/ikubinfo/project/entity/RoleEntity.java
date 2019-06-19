package com.ikubinfo.project.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name="role")
public class RoleEntity {
	@Id
	@Column(name="role_id")
	private int id;
	@Column (name="role_name")
	private String roleName;
	@Column(name="role_description")
	private String roleDescription;

	public RoleEntity() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", roleName=" + roleName + ", roleDescription=" + roleDescription + "]";
	}
	
	
	
}
