package com.lzy.jurisdcition.ssh.common.sys.service;

import java.util.List;

import com.lzy.jurisdcition.ssh.common.sys.criteria.SysRoleCriteria;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import com.lzy.jurisdcition.ssh.common.util.PageBean;


@SuppressWarnings("rawtypes")
public interface SysRoleService {
	
	public void add(SysRole SysRole);
	public void delete(String ids);
	public void update(SysRole SysRole);
	public SysRole get(int id);
	public List list();
	public void findByPage(PageBean pb,SysRoleCriteria sysrole);
	public int findMaxPage(int rowsPerPage);
	public boolean existsName(String name);
	public boolean existsName(String name,Integer roleId);
	public List getAllPermissionsIds(int roleId);

}
