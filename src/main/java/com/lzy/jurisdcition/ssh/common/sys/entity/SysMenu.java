package com.lzy.jurisdcition.ssh.common.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * 菜单实体
 */
@JsonIgnoreProperties(value = {"sysRoles" , "sysOperations" , "sysMenus" ,"handler","hibernateLazyInitializer"})
public class SysMenu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer menuId;
	private String menuName;
	private SysMenu menuParent;
	private String menuAction;
	private Integer menuSortOrder;
	private String menuIcon;
	private String menuRemark;
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	private Set<SysOperation> sysOperations = new HashSet<SysOperation>(0);
	private Set<SysMenu> sysMenus = new HashSet<SysMenu>(0);
	public SysMenu() {
		super();
	}
	public SysMenu(Integer menuId, String menuName, SysMenu menuParent, String menuAction,
			Integer menuSortOrder, String menuIcon, String menuRemark, Set<SysRole> sysRoles,
			Set<SysOperation> sysOperations, Set<SysMenu> sysMenus) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuParent = menuParent;
		this.menuAction = menuAction;
		this.menuSortOrder = menuSortOrder;
		this.menuIcon = menuIcon;
		this.menuRemark = menuRemark;
		this.sysRoles = sysRoles;
		this.sysOperations = sysOperations;
		this.sysMenus = sysMenus;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public SysMenu getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(SysMenu menuParent) {
		this.menuParent = menuParent;
	}
	public String getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	public Integer getMenuSortOrder() {
		return menuSortOrder;
	}
	public void setMenuSortOrder(Integer menuSortOrder) {
		this.menuSortOrder = menuSortOrder;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getMenuRemark() {
		return menuRemark;
	}
	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}
	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}
	
	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
	
	public Set<SysOperation> getSysOperations() {
		return sysOperations;
	}
	
	public void setSysOperations(Set<SysOperation> sysOperations) {
		this.sysOperations = sysOperations;
	}
	
	public Set<SysMenu> getSysMenus() {
		return sysMenus;
	}

	public void setSysMenus(Set<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	
	
}
