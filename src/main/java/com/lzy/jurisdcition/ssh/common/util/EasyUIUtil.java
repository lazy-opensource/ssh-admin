package com.lzy.jurisdcition.ssh.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.lzy.jurisdcition.ssh.common.base.dao.BaseDao;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;

@Component
public class EasyUIUtil {
	
	@Resource(name = "baseDao")
	private static BaseDao baseDao;
	
	//生成菜单树
	public static List<EasyUITreeEntity> getMenuTree(Set<SysMenu> menus){
		List<EasyUITreeEntity> menuTrees = new ArrayList<EasyUITreeEntity>();
		List<SysMenu> rootMenus = new ArrayList<SysMenu>();
		List<SysMenu> parentMenus = new ArrayList<SysMenu>();
		for(SysMenu rootMenu : menus){//遍历菜单集合
			if(rootMenu.getMenuParent() == null){//没有父节点===》根节点
				rootMenus.add(rootMenu);
			}else{
				parentMenus.add(rootMenu);
			}
		}
		if(rootMenus.size() > 0){
			sortMenu(rootMenus);//排序
			for(SysMenu rootMenu : rootMenus){//再次遍历根菜单
				EasyUITreeEntity rootMenuEntity = new EasyUITreeEntity();
				rootMenuEntity.setId(rootMenu.getMenuId()+"");
				rootMenuEntity.setState("open");
				rootMenuEntity.setText(rootMenu.getMenuName());
				rootMenuEntity.setRemark(rootMenu.getMenuRemark());
				rootMenuEntity.setIconCls(rootMenu.getMenuIcon());
				rootMenuEntity.setType(EasyUITreeEntity.MENU); 
				//baseDao.initializeDeep(rootMenu.getSysMenus());
				if(rootMenu.getSysMenus() != null && rootMenu.getSysMenus().size() > 0){//是否存在子菜单
					recursionChildMenus(menus , rootMenu.getSysMenus(),rootMenuEntity);
				}
				menuTrees.add(rootMenuEntity);
			}
		}else{
			sortMenu(parentMenus);//排序
			for(SysMenu rootMenu : parentMenus){//再次遍历根菜单
				EasyUITreeEntity rootMenuEntity = new EasyUITreeEntity();
				rootMenuEntity.setId(rootMenu.getMenuId()+"");
				rootMenuEntity.setState("open");
				rootMenuEntity.setUrl(rootMenu.getMenuAction());
				rootMenuEntity.setText(rootMenu.getMenuName());
				rootMenuEntity.setRemark(rootMenu.getMenuRemark());
				rootMenuEntity.setIconCls(rootMenu.getMenuIcon());
				rootMenuEntity.setType(EasyUITreeEntity.MENU); 
				//baseDao.initializeDeep(rootMenu.getSysMenus());
				if(rootMenu.getSysMenus() != null && rootMenu.getSysMenus().size() > 0){//是否存在子菜单
					recursionChildMenus(menus , rootMenu.getSysMenus(),rootMenuEntity);
				}
				menuTrees.add(rootMenuEntity);
			}
		}
		return menuTrees;
	}
	private static void recursionChildMenus(Set<SysMenu> menus ,Set<SysMenu> childs ,EasyUITreeEntity menuEntity){
		List<SysMenu> notSortMenus = new ArrayList<SysMenu>();
		for(SysMenu tempMenu : childs){
			for(SysMenu menu : menus){
				if(tempMenu.getMenuId() == menu.getMenuId()){//该角色拥有这个子菜单的权限
					recursionChildMenu(menus,menu,menuEntity);
				}
			}
		}
		sortMenu(notSortMenus);
		for(SysMenu menu : notSortMenus){
			recursionChildMenu(menus,menu,menuEntity);
		}
	}
	
	//添加一个子菜单
	private static void recursionChildMenu(Set<SysMenu> menus ,SysMenu childMenu  ,EasyUITreeEntity menuEntity){
		EasyUITreeEntity childMenuEntity = new EasyUITreeEntity();
		childMenuEntity.setId(childMenu.getMenuId()+"");
		childMenuEntity.setIconCls(childMenu.getMenuIcon());
		childMenuEntity.setUrl(childMenu.getMenuAction());
		childMenuEntity.setType(EasyUITreeEntity.MENU);
		childMenuEntity.setRemark(childMenu.getMenuRemark());
		childMenuEntity.setState("open");
		childMenuEntity.setText(childMenu.getMenuName());
		menuEntity.getChildren().add(childMenuEntity);
		//baseDao.initializeDeep(childMenu.getSysOperations());
		if(childMenu.getSysMenus() != null && childMenu.getSysMenus().size() > 0){//存在子菜单
			recursionChildMenus(menus,childMenu.getSysMenus(),childMenuEntity);
		}
	}
	
	//递归菜单子节点
	@SuppressWarnings("unused")
	private static void recursionRootMenus(EasyUITreeEntity parentMenuEntity , Set<SysMenu> childMenus){
		List<SysMenu> childList = new ArrayList<SysMenu>();
		for(SysMenu childMenu : childMenus){
			childList.add(childMenu);
		}
		sortMenu(childList);
		for(SysMenu childMenu : childList){
			EasyUITreeEntity childMenuEntity = new EasyUITreeEntity();
			childMenuEntity.setId(childMenu.getMenuId()+"");
			childMenuEntity.setIconCls(childMenu.getMenuIcon());
			childMenuEntity.setUrl(childMenu.getMenuAction());
			childMenuEntity.setType(EasyUITreeEntity.MENU);
			childMenuEntity.setRemark(childMenu.getMenuRemark());
			childMenuEntity.setState("open");
			childMenuEntity.setText(childMenu.getMenuName());
			parentMenuEntity.getChildren().add(childMenuEntity);
			if(childMenu.getSysMenus() != null && childMenu.getSysMenus().size() > 0){
				baseDao.initializeDeep(childMenu.getSysMenus());
				recursionRootMenus(childMenuEntity,childMenu.getSysMenus());//递归	
			}
		}	
	}
	

	//自定义排序规则
	private static void sortMenu(List<SysMenu> sysMenu){
		Collections.sort(sysMenu, new Comparator<SysMenu>(){
			public int compare(SysMenu o1, SysMenu o2) {
				return o1.getMenuSortOrder() - o2.getMenuSortOrder();
			}
		});
	}	
	

	
}
