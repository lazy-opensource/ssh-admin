package com.lzy.jurisdcition.ssh.common.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzy.jurisdcition.ssh.common.base.service.BaseService;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.sys.service.SysMenuService;
import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

//@Transactional
@Service(value = "sysMenuService")
public class SysMenuServiceImpl extends BaseService implements SysMenuService {

	public void add(SysMenu sysMenu) {
		baseDao.add(sysMenu);
	}

	public void delete(SysMenu sysMenu) {
		baseDao.updateByHql("delete from SysOperation  where sysMenu.menuId = ?", sysMenu.getMenuId());
		baseDao.delete(sysMenu);

	}

	public void update(SysMenu sysMenu) {
		baseDao.update(sysMenu);

	}

	public SysMenu get(int id) {
		SysMenu menu = baseDao.get(SysMenu.class, id);
		baseDao.initialize(menu.getSysRoles());
		baseDao.initialize(menu.getSysOperations());
		baseDao.initialize(menu.getSysMenus());
		return menu;
	}

	public List<SysMenu> list() {
		@SuppressWarnings("unchecked")
		List<SysMenu> list = baseDao.findAll(SysMenu.class);
//		for(SysMenu sm : list ){
//			//baseDao.initialize(sm.getSysMenus());
//			//baseDao.initialize(sm.getSysRoles());
//			//baseDao.initialize(sm.getSysOperations());
//		}
		return list;
	}

	public List<SysMenu> listAll() {
		@SuppressWarnings("unchecked")
		List<SysMenu> list = baseDao.findAll(SysMenu.class);
		for(SysMenu sm : list ){
			baseDao.initialize(sm.getSysMenus());
			baseDao.initialize(sm.getSysRoles());
			baseDao.initialize(sm.getSysOperations());
		}
		return list;
	}

	public int getMaxSortOrder(Integer parentId) {
		Object obj = null;
		if(parentId != -1){
			obj = baseDao.findVal("select max(menuSortOrder) from SysMenu where menuId = ?", parentId);
		}else{
			obj = baseDao.findVal("select max(menuSortOrder) from SysMenu where menuId is null");
		}
		return obj != null ? Integer.valueOf(obj.toString()) : 0;
	}

	public int getSortOrder(Integer menuId) {
		
		return baseDao.findVal("select menuSortOrder from SysMenu where menuId = ?", menuId);
	}

	public void move(Integer menuId, boolean up) {
		
		//查出
		SysMenu menu = baseDao.get(SysMenu.class,menuId);

		//下移
		String hql = "from SysMenu where menuParent.menuId = ? and menuSortOrder = (select min(menuSortOrder) from SysMenu where menuParent.menuId = ? and menuSortOrder > ?)";
	    if(up){//上移
	    	hql = "from SysMenu where menuParent.menuId = ? and menuSortOrder = (select max(menuSortOrder) from SysMenu where menuParent.menuId = ? and menuSortOrder < ?)";
	    }
	    Integer parentId = null;
	    if(menu != null && menu.getMenuParent() != null){//如果有父菜单
	    	parentId = menu.getMenuParent().getMenuId();//父菜单Id
	    }
	    //相邻交换的对象
	    SysMenu neighbour = baseDao.findVal(hql, parentId , parentId , menu.getMenuSortOrder());
	    if(menu!=null && neighbour!=null){
	    	if(neighbour.getMenuId() != menu.getMenuId()){
				int temp = neighbour.getMenuSortOrder();
				neighbour.setMenuSortOrder(menu.getMenuSortOrder());
				menu.setMenuSortOrder(temp);
			}
	    	baseDao.update(neighbour);
	    }
		
		baseDao.update(menu);
		
	}

	public boolean hashChildMenu(Integer menuId) {
		
		return ((SysMenu)(baseDao.get(SysMenu.class, menuId))).getSysMenus().size() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<String> getIdsByRoleId(Integer roleId) {
		
		return baseDao.findBySQL("select menu_id from sys_role_menu where role_id = ?", roleId);
	}

	public void findByPage(PageBean<SysMenu> pageBean, BaseCriteria baseCriteria) {
		//HQL子查询只能出现在select或where字句中
		//pageBean.setSelect("select ");
		pageBean.setEntityName("SysMenu s");
		baseDao.findByPage(pageBean, baseCriteria);
		
	}

	public Integer delete(String ids) {
		return baseDao.deleteByValues(SysMenu.class, "menuId", ids);
	}

}
