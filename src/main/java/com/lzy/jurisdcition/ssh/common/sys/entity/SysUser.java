package com.lzy.jurisdcition.ssh.common.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/*
 * 用户实体
 */
public class SysUser implements Serializable{

	public static final int STATUS_LOCK=1;
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String userName;
	private String userRealName;
	private String userPassword;
	private Integer userStatus;
	private String userRemark;
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userPassword == null) ? 0 : userPassword.hashCode());
		result = prime * result
				+ ((userRealName == null) ? 0 : userRealName.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUser other = (SysUser) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		if (userRealName == null) {
			if (other.userRealName != null)
				return false;
		} else if (!userRealName.equals(other.userRealName))
			return false;
		if (userStatus == null) {
			if (other.userStatus != null)
				return false;
		} else if (!userStatus.equals(other.userStatus))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

    public SysUser(Integer userId, String userName, String userRealName, String userPassword, Integer userStatus, String userRemark, Set<SysRole> sysRoles) {
        this.userId = userId;
        this.userName = userName;
        this.userRealName = userRealName;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.userRemark = userRemark;
        this.sysRoles = sysRoles;
    }

    public SysUser() {
		super();
	}

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}
	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}


	
	
}
