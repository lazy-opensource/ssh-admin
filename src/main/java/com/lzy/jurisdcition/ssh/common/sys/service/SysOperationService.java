package com.lzy.jurisdcition.ssh.common.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

public interface SysOperationService {

	public void add(SysOperation sysOperation);
	public Integer delete(String ids);
	public void deleteByMenuPermissionId(int menuPermissionId);
	public void delete(SysOperation sysOperation);
	public void update(SysOperation sysOperation);
	public SysOperation get(int id);
	@SuppressWarnings("rawtypes")
	public List<Map> list(Integer menuId);
	public List<String> getIdsByRoleId(Integer roleId);
	public Map<String,String> getAllOpreationNames();
	public void findByPage(PageBean<SysOperation> pagin, BaseCriteria baseCriteria);
    public List<SysOperation> findAll();
}
