package com.lzy.jurisdcition.ssh.common.sys.criteria;


import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.StringUtils;

/**
 * SysRole entity.
 * 
 * @author easyproject.cn
 * @version 1.0
 */
public class SysRoleCriteria extends BaseCriteria implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private String name;
	private Integer status;
	
	public SysRoleCriteria(String name, Integer status) {
		super();
		this.name = name;
		this.status = status;
	}

	public SysRoleCriteria() {
		super();
	}

	/*
 	 * 3. 条件生成抽象方法实现
 	 */
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotNullAndEmpty(this.getName())){
			condition.append(" and name like ?");
			values.add("%"+this.getName()+"%");
		}
		if(StringUtils.isNotNullAndEmpty(this.getStatus())){
			condition.append(" and status=?");
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}