package com.lzy.jurisdcition.ssh.common.base.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lzy.jurisdcition.ssh.common.base.dao.BaseDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.sys.service.SysMenuService;
import com.lzy.jurisdcition.ssh.common.sys.service.SysOperationService;
import com.lzy.jurisdcition.ssh.common.sys.service.SysRoleService;
import com.lzy.jurisdcition.ssh.common.sys.service.SysUserService;
import com.lzy.jurisdcition.ssh.common.util.EasyUITreeEntity;
import com.lzy.jurisdcition.ssh.common.util.EasyUIUtil;

@RequestMapping("/base")
@Controller
public class BaseController {


	private Integer mId = null;//保存菜单的ID
	
	@Resource(name = "sysUserService")
	protected SysUserService sysUserService;
	
	@Resource(name = "sysMenuService")
	protected SysMenuService sysMenuService;
	
	@Resource(name = "sysOperationService")
	protected SysOperationService sysOperationService;
	
	@Resource(name = "sysRoleService")
	protected SysRoleService sysRoleService;

	
//	@Resource(name = "baseDao")
//	protected BaseDao baseDao;
	
	//自定义排序规则
	protected static void sortOperation(List<SysOperation> sysOperation){
		Collections.sort(sysOperation, new Comparator<SysOperation>(){
			public int compare(SysOperation o1, SysOperation o2) {
				return o1.getOperationId() - o2.getOperationId();
			}
		});
	}
	
	protected List<EasyUITreeEntity>  setJurisdiction(HttpServletRequest request) {
		List<EasyUITreeEntity> list = null;
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		if(sysUser != null){
			Set<SysMenu> menus = new HashSet<SysMenu>();//菜单
			Set<SysRole> roles = sysUser.getSysRoles();//角色
//			List<SysOperation> operationList = new ArrayList<SysOperation>();
			for(SysRole role : roles){//遍历用户的所有角色
				Set<SysMenu> menu = role.getSysMenus();//每个角色拥有的菜单
//				Set<SysOperation> operations = role.getSysOperations();
				for(SysMenu m : menu){//遍历菜单
					menus.add(m);//这里避免了重复的菜单   用Set
				}
//				for(SysOperation oper : operations){
//					operationList.add(oper);
//				}
				list = EasyUIUtil.getMenuTree(menus);//生成菜单
			}
//			String str = JSON.toJSONString(operationList);
//			session.setAttribute("operations", str);
		}	
		return list;
	}
	
	protected boolean isNotNullAndEmpty(Object str) {
		return str != null && (!str.equals(""));
	}
	

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}
	

}