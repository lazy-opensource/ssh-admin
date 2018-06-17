package com.lzy.jurisdcition.ssh.common.sys.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lzy.jurisdcition.ssh.common.util.*;
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

@Controller
@RequestMapping("/role")
public class SysRoleController extends BaseController{
	
	
	private Logger log = Logger.getLogger(SysRoleController.class);
	
	/**
	 * 跳转到列表
	 * @return
	 */
	@RequestMapping(value = "/toList" , method = RequestMethod.GET)
	public String toList(@RequestParam(value = "menuId") Integer menuId){
		super.setmId(menuId); 
		return "list/sys_role";
	}
 
	@ResponseBody
	@RequestMapping(value = "/update" , produces="application/json", method = RequestMethod.POST)
	public MyJson update(SysRole sysRole,String menuIds ,String operationIds){
		MyJson json = new MyJson();
		try{
            String mIds[] = "".equals(menuIds) ? null : menuIds.split(",");
            String oIds[] = "".equals(operationIds) ? null : operationIds.split(",");
            Set<SysMenu> m = new HashSet<SysMenu>();
            Set<SysOperation> o = new HashSet<SysOperation>();
            SysRole oldRole = sysRoleService.get(sysRole.getRoleId());
            oldRole.setRoleName(sysRole.getRoleName());
            oldRole.setRoleRemark(sysRole.getRoleRemark());
            oldRole.setRoleStatus(sysRole.getRoleStatus());

		if(mIds != null){
			for(int i=0;i<mIds.length; i++){
				SysMenu sysMenu = new SysMenu();
				sysMenu.setMenuId(Integer.valueOf(mIds[i]));
				m.add(sysMenu);
			}
			oldRole.setSysMenus(m);
		}
        if(oIds != null){
            for(int i=0;i<oIds.length; i++){
                SysOperation sysOperation = new SysOperation();
                sysOperation.setOperationId(Integer.valueOf(oIds[i]));
                o.add(sysOperation);
            }
            oldRole.setSysOperations(o);
        }
		sysRoleService.update(oldRole);
		json.setMsg("修改成功!");
		json.setStatusCode(StatusCode.OK);	
		}catch(RuntimeException e){
			log.info("调用role/update 方法出错了>>>>>>>>>>>");
			json.setMsg("修改失败");
			json.setStatusCode(StatusCode.ERROR);
			throw e;
		
		}
		
		return json;
	}

	/**
	 * 角色列表
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list" , produces = "application/json" , method = RequestMethod.POST)
	public PageBean<SysRole> list(String rows,String page){
			PageBean<SysRole> pagin = new PageBean<SysRole>();
	   try{
			pagin.setRowsPerPage(rows != null ?Integer.valueOf(rows) : 10);//每页多少行
			pagin.setPageNo(page != null ? Integer.valueOf(page) : 1);//当前第几页  	
			sysRoleService.findByPage(pagin, null);
			//System.out.println(((SysRole)pagin.getData().get(0)).getSysMenus());
			pagin.setRows(pagin.getData());//返回的数据
			pagin.setTotal(pagin.getRowsCount());//总条数
			pagin.setPage(((Integer)(pagin.getPageNo())).toString());//当前第几页 
		}catch(RuntimeException e){
			log.info("调用role/list 方法出错了>>>>>>>>>>>");
			throw e;
		
		}
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
        MyJson json = new MyJson();
		HttpSession session = request.getSession();
        List<SysOperation> operationList = new ArrayList<SysOperation>();
        Set<SysOperation> operationSet = new HashSet<SysOperation>();
		SysUser sysUser = (SysUser) session.getAttribute("user");
        Set<SysRole> roles = null;
        if(sysUser != null && sysUser.getUserId() == 1){
            operationList = sysOperationService.findAll();
            roles = sysUser.getSysRoles();//角色
            for(SysRole role : roles){//遍历用户的所有角色
                Set<SysOperation> operations = role.getSysOperations();
                for(SysOperation operation : operations){
                    operationSet.add(operation);
                }
            }
            for(SysOperation operation : operationList){//遍历用户的所有角色
                operationSet.add(operation);
            }
            operationList.clear();
            for(SysOperation operation : operationSet){//遍历用户的所有角色
                operationList.add(operation);
            }

        }else{
            roles = sysUser.getSysRoles();//角色
            for(SysRole role : roles){//遍历用户的所有角色
                Set<SysOperation> operations = role.getSysOperations();
                for(SysOperation oper : operations){
                    operationSet.add(oper);
                }
            }
            for(SysOperation operation : operationSet){//遍历用户的所有角色
                operationList.add(operation);
            }
        }

		super.sortOperation(operationList);
        json.setData(operationList);
        json.setStatusCode(super.getmId());
		return json;
	}

    /**
     * 获得菜单树
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menuTree" , produces = "application/json" , method = RequestMethod.POST)
    public MyJson getMenuTree(){
        MyJson json = new MyJson();
        List<SysMenu> sysMenuList = sysMenuService.list();
        Set<SysMenu> sysMenuSet = new HashSet<SysMenu>();
        for(int i=0;i<sysMenuList.size();i++){
            sysMenuSet.add(sysMenuList.get(i));
        }
        List<EasyUITreeEntity> data = EasyUIUtil.getMenuTree(sysMenuSet);//生成菜单
        json.setData(data);
        log.info("调用getMenuTree==>>"+json);
        return json;
    }

	/**
	 * 返回所有的角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/roles" , produces = "application/json" , method = RequestMethod.POST)
	public List<SysRole> roles(){
		List<SysRole> roles = sysRoleService.list();
		return roles;
	}
	

	@ResponseBody
	@RequestMapping(value = "/add" , produces="application/json" , method = RequestMethod.POST)
	public MyJson add(SysRole sysRole , String menuIds , String operationIds){
		MyJson json = new MyJson();
		String mIds[] = menuIds.split(",");
		String oIds[] = operationIds.split(",");
		Set<SysMenu> m = new HashSet<SysMenu>();
		Set<SysOperation> o = new HashSet<SysOperation>();
		
		if(mIds != null && mIds.length >1){
			for(int i=0;i<mIds.length; i++){
				SysMenu sysMenu = sysMenuService.get(Integer.valueOf(mIds[i]));
				m.add(sysMenu);
			}
			sysRole.setSysMenus(m);
		}
        if(oIds != null&& oIds.length >1){
			for(int i=0;i<oIds.length; i++){
				SysOperation sysOperation = sysOperationService.get(Integer.valueOf(oIds[i]));
				o.add(sysOperation);
			}
			sysRole.setSysOperations(o);
		}
		try{
			sysRoleService.add(sysRole);
			json.setMsg("添加成功");
			json.setStatusCode(StatusCode.OK);
		}catch(RuntimeException e){
			json.setStatusCode(StatusCode.ERROR);
			json.setMsg("添加失败");
			log.info("调用role/add方法出错了 >>>>>>>>>>>" );
			throw e;
		}
		return json;
	}
	

	@ResponseBody
	@RequestMapping(value = "/delete/{ids}" , produces="application/json", method = RequestMethod.POST)
	public MyJson delete(@PathVariable(value = "ids") String ids){
		MyJson json = new MyJson();
//		String rIds[] = null;
//		if(ids != null){
//			rIds = ids.split(",");
//            for(int id = 0; id < rIds.length ; id++){
//                if(rIds[id] == "1"){
//                    json.setMsg("没有删除超级管理员角色权限!");
//                    return json;
//                }
//            }
//		}

		try{
			sysRoleService.delete(ids);
			json.setMsg("删除成功");
			json.setStatusCode(StatusCode.OK);
		}catch(RuntimeException e){
			json.setMsg("删除失败");
			json.setStatusCode(StatusCode.OK);
			log.info("调用role/delete方法出错了>>>>>>>>>>>");
			throw e;
		}
		
		
		return json;
	}
}
