package com.lzy.jurisdcition.ssh.common.sys.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzy.jurisdcition.ssh.common.base.controller.BaseController;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysMenu;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.util.MyJson;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

@Controller
@RequestMapping("/menu")
public class SysMenuController extends BaseController{
	
	
	private Logger log = Logger.getLogger(SysMenuController.class);
	/**
	 * 添加
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add" ,  produces = "application/json",  method = RequestMethod.POST)
	public MyJson add(SysMenu sysMenu , String menuParents){
		MyJson json = new MyJson();
		try{
			String ids[] = menuParents != "" ? menuParents.split(",") : null;
			if(ids != null){
				for(int i =0;i<ids.length;i++){
			    	SysMenu m = new SysMenu();
                    SysMenu sub = new SysMenu();
			    	m.setMenuId(Integer.valueOf(ids[i]));
                    sub.setMenuAction(sysMenu.getMenuAction());
                    sub.setMenuIcon(sysMenu.getMenuIcon());
                    sub.setMenuName(sysMenu.getMenuName());
                    sub.setMenuParent(m);
                    sub.setMenuRemark(sysMenu.getMenuRemark());
                    sub.setMenuSortOrder(sysMenu.getMenuSortOrder());
			    	sysMenuService.add(sub);
			    }
			}else{
				sysMenuService.add(sysMenu);
			}
		    json.setMsg("添加成功");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用menu/add 出错了>>>>>>>>>>>");
			throw e;
		}
		
	    return json;
	}
	/**
	 * 批量/单条删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{ids}" , produces = "application/json", method = RequestMethod.POST)
	public MyJson delete(@PathVariable(value = "ids") String ids){
		MyJson json = new MyJson();
//        String menuIds[] = ids != null ? ids.split(",") : null;
//        if(menuIds != null){
//            for(int id = 0; id < menuIds.length; id++){
//                if(Integer.valueOf(menuIds[id]) < 6){
//                    json.setMsg("没有删除\nSSH\n权限管理\n角色管理\n用户管理\n操作管理\n菜单管理\n日志管理\n等菜单的权限!");
//                    return json;
//                }
//            }
//        }
		try{
			Integer rows = sysMenuService.delete(ids);
		    json.setMsg("成功删除"+rows+"条数据");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用menu/delete出错了>>>>>>>>>");
			throw e;
		}
	    return json;
	}
	
	/**
	 * 修改
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update" ,  produces = "application/json",  method = RequestMethod.POST)
	public MyJson update(SysMenu sysMenu , String parentIds){
		MyJson json = new MyJson();
        SysMenu oldMenu = sysMenuService.get(sysMenu.getMenuId());
		try{
			String ids[] = parentIds != "" ? parentIds.split(",") : null;
           // System.out.println("**********************");
			//System.out.println(ids != null);
	        if(ids != null){
	        	for(int i = 0;i < ids.length; i++){
                    SysMenu m = new SysMenu();
                    SysMenu sub = new SysMenu();
                    m.setMenuId(Integer.valueOf(ids[i]));
                   // sub.setMenuAction(oldMenu.getMenuAction());
                    sub.setMenuIcon(sysMenu.getMenuIcon());
                    sub.setMenuName(sysMenu.getMenuName());
                    sub.setMenuParent(m);
                    sub.setMenuRemark(sysMenu.getMenuRemark());
                    sub.setMenuSortOrder(sysMenu.getMenuSortOrder());
                    //sub.setSysRoles(oldMenu.getSysRoles());
                    sub.setMenuAction(oldMenu.getMenuAction());
                    //sub.setSysMenus(oldMenu.getSysMenus());
                    //sub.setSysOperations(oldMenu.getSysOperations());
                    //sub.setMenuId(sysMenu.getMenuId());
                    sysMenuService.add(sub);
	            }
	        }else{
                //oldMenu.setMenuAction(sysMenu.getMenuAction());
                oldMenu.setMenuIcon(sysMenu.getMenuIcon());
                oldMenu.setMenuName(sysMenu.getMenuName());
                oldMenu.setMenuRemark(sysMenu.getMenuRemark());
                oldMenu.setMenuSortOrder(sysMenu.getMenuSortOrder());
	        	sysMenuService.update(oldMenu);
	        }
		    json.setMsg("修改成功");
		    json.setStatusCode(200);
		}catch(RuntimeException e){
			log.info("调用menu/update 出错了>>>>>>>");
			throw e;
		}
	    return json;
	}

	@RequestMapping(value = "/toList" , method = RequestMethod.GET)
	public String toList(@RequestParam(value = "menuId") Integer menuId){
		super.setmId(menuId);
		return "list/sys_menu";
	}
	
	/**
	 * 菜单列表 json
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list" , produces = "application/json",  method = RequestMethod.POST)
	public PageBean<SysMenu> list(String rows,String page){
		PageBean<SysMenu> pagin = new PageBean<SysMenu>();
		pagin.setRowsPerPage(rows != null ?Integer.valueOf(rows) : 10);//每页多少行
		pagin.setPageNo(page != null ? Integer.valueOf(page) : 1);//当前第几页  	
		sysMenuService.findByPage(pagin, null);
		List<SysMenu> list = pagin.getData();
		pagin.setRows(list);//返回的数据
		pagin.setTotal(pagin.getRowsCount());//总条数
		pagin.setPage(((Integer)(pagin.getPageNo())).toString());//当前第几页 
		
		return pagin;
	}
	
	/**
	 * 获得操作列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/operations" , produces = "application/json" ,method = RequestMethod.GET)
	public MyJson getOperation(HttpServletRequest request ){
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		List<SysOperation> operationList = new ArrayList<SysOperation>();
		Set<SysOperation> operationSet = new HashSet<SysOperation>();
		MyJson json = new MyJson();
		Set<SysRole> roles = sysUser.getSysRoles();//角色
		
		for(SysRole role : roles){//遍历用户的所有角色
			Set<SysOperation> operations = role.getSysOperations();
			for(SysOperation oper : operations){
				operationSet.add(oper);
			}
		}
		for(SysOperation operation : operationSet){//遍历用户的所有角色
			operationList.add(operation);
		}
		super.sortOperation(operationList);
        json.setData(operationList);
        json.setStatusCode(super.getmId());
		return json;
	}

}
