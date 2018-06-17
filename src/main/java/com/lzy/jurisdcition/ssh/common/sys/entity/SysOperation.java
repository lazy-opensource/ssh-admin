package com.lzy.jurisdcition.ssh.common.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * 操作实体
 */
@JsonIgnoreProperties(value = {"sysRoles" ,"handler","hibernateLazyInitializer"})
public class SysOperation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer operationId;
	private String operationName;
	private SysMenu sysMenu;
	private String operationAction;
	private String operationRemark;
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	public SysOperation() {
		super();
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((operationId == null) ? 0 : operationId.hashCode());
        result = prime * result
                + ((operationName == null) ? 0 : operationName.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(getClass() != obj.getClass())
            return false;
        SysOperation that = (SysOperation)obj;
        if(that.getOperationId() != null){
            if(operationId == null){
                return false;
            }
        }
        if(that.getOperationName() != null){
            if(operationName == null){
                return false;
            }
        }
        return true;
    }

    public SysOperation(Integer operationId, String operationName, SysMenu sysMenu, String operationAction,
			String operationRemark, Set<SysRole> sysRoles) {
		super();
		this.operationId = operationId;
		this.operationName = operationName;
		this.sysMenu = sysMenu;
		this.operationAction = operationAction;
		this.operationRemark = operationRemark;
		this.sysRoles = sysRoles;
	}
	public Integer getOperationId() {
		return operationId;
	}
	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public SysMenu getSysMenu() {
		return sysMenu;
	}
	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}
	public String getOperationAction() {
		return operationAction;
	}
	public void setOperationAction(String operationAction) {
		this.operationAction = operationAction;
	}
	public String getOperationRemark() {
		return operationRemark;
	}
	public void setOperationRemark(String operationRemark) {
		this.operationRemark = operationRemark;
	}

	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	
}
