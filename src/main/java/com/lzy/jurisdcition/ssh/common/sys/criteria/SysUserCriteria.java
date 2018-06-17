package com.lzy.jurisdcition.ssh.common.sys.criteria;

import java.io.Serializable;
import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.StringUtils;

public class SysUserCriteria extends BaseCriteria implements Serializable{

	private static final long serialVersionUID = 1L;
	/*
	 * 条件属性
	 */
	private String name;
	private String realName;
	private Integer status; // 用户状态：0启用；1禁用；2删除
	public SysUserCriteria() {
		super();
	}
	public SysUserCriteria(String name, String realName, Integer status) {
		super();
		this.name = name;
		this.realName = realName;
		this.status = status;
	}
	@Override
	public String getCondition() {
		values.clear();//父类属性   清除
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotNullAndEmpty(this.getName())){
			condition.append(" and ").append(this.getName()).append(" like ?");
			values.add("%"+this.getName()+"%");
		}
		if(StringUtils.isNotNullAndEmpty(this.getRealName())){
			condition.append(" and ").append(this.getRealName()).append(" like ?");
			values.add("%" +this.getRealName()+"%");
		}
		if(StringUtils.isNotNullAndEmpty(this.getStatus())){
			condition.append(" and ").append(this.getStatus()).append(" = ?");
			values.add(this.getStatus());
		}
		return condition.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
