package com.lzy.jurisdcition.ssh.common.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * 角色实体
 */

@JsonIgnoreProperties(value = {"sysUsers" ,"handler","hibernateLazyInitializer"})
public class SysRole implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer roleId;
	private String roleName;
	private String roleStatus;
	private String roleRemark;
	
	private Set<SysMenu> sysMenus = new HashSet<SysMenu>(0);
	private Set<SysOperation> sysOperations = new HashSet<SysOperation>(0);
	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);
	public SysRole() {
		super();
	}
	public SysRole(Integer roleId, String roleName, String roleStatus, String roleRemark, Set<SysMenu> sysMenus,
			Set<SysOperation> sysOperations, Set<SysUser> sysUsers) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleStatus = roleStatus;
		this.roleRemark = roleRemark;
		this.sysMenus = sysMenus;
		this.sysOperations = sysOperations;
		this.sysUsers = sysUsers;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	public String getRoleRemark() {
		return roleRemark;
	}
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

	public Set<SysMenu> getSysMenus() {
		return sysMenus;
	}
	
	public void setSysMenus(Set<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}
	public Set<SysOperation> getSysOperations() {
		return sysOperations;
	}
	public void setSysOperations(Set<SysOperation> sysOperations) {
		this.sysOperations = sysOperations;
	}
	
	public Set<SysUser> getSysUsers() {
		return sysUsers;
	}
	
	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

    
}
