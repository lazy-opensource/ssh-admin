package com.lzy.jurisdcition.ssh.common.sys.service;

import com.lzy.jurisdcition.ssh.common.sys.criteria.SysUserCriteria;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

public interface SysUserService {
	
	public SysUser login(SysUser sysUser);
	public void save(SysUser sysuser);
	public Integer delete(String[] ids);
	public void delete(Integer id);
	public void update(SysUser sysuser);
	public SysUser get(int id);
	public void findByPage(PageBean<SysUser> pb,SysUserCriteria sysuser);
	public void changePwd(int id,String pwd);
	public boolean existsName(String name);
	public boolean existsName(String name,Integer userId);
	public int findMaxPage(int rowsPerPage);
	public String getPwd(int id);

}
