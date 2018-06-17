package com.lzy.jurisdcition.ssh.common.sys.service;

import java.util.List;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

public interface SysMenuService {

	public void add(SysMenu sysMenu);
	public void delete(SysMenu sysMenu);
	public Integer delete(String ids);
	public void update(SysMenu sysMenu);
	public SysMenu get(int id);
	public List<SysMenu> list();
	public List<SysMenu> listAll();	
	public int getMaxSortOrder(Integer parentId);
	public int getSortOrder(Integer menuId);
	public void move(Integer menuId, boolean up);	
	public boolean hashChildMenu(Integer menuId);
    public List<String> getIdsByRoleId(Integer roleId);
	public void findByPage(PageBean<SysMenu> pagin, BaseCriteria criteria);
	
}
