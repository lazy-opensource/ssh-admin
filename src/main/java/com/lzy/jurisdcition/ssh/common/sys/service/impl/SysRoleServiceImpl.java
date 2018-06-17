package com.lzy.jurisdcition.ssh.common.sys.service.impl;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzy.jurisdcition.ssh.common.base.service.BaseService;
import com.lzy.jurisdcition.ssh.common.sys.criteria.SysRoleCriteria;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysRole;
import com.lzy.jurisdcition.ssh.common.sys.service.SysRoleService;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

//@Transactional
@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseService implements SysRoleService {

	public void add(SysRole SysRole) {
		baseDao.add(SysRole);
	}

	public void delete(String ids) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("(");
//		for(int i  =0;i <ids.length; i++){
//			sb.append("'"+ids[i]+"',");
//		}
//		sb.deleteCharAt(sb.length()-1);
//		sb.append(")");
		baseDao.deleteByValues(SysRole.class, "roleId", ids);

	}

	public void update(SysRole SysRole) {
		baseDao.update(SysRole);

	}

	public SysRole get(int id) {
		
		return baseDao.get(SysRole.class, id);
	}

	@SuppressWarnings("rawtypes")
	public List list() {
		
		return baseDao.findByCache("from SysRole");
	}

	@SuppressWarnings("rawtypes")
	public void findByPage( PageBean pb, SysRoleCriteria sysrole) {
		pb.setEntityName("SysRole");
		baseDao.findByPage(pb,sysrole);
	}

	public int findMaxPage(int rowsPerPage) {
		
		return baseDao.findMaxPage("select count(*) from SysRole", rowsPerPage);
	}

	public boolean existsName(String name) {
		
		return baseDao.findCount("select count(*) from SysRole where roleName = ?", name) > 0 ;
	}

	public boolean existsName(String name, Integer roleId) {
		
	    return baseDao.findCount("select count(*) from SysRole where roleName = ? and roleId = ?", name , roleId) > 0 ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllPermissionsIds(int roleId) {
		//查询角色的菜单权限Id和操作权限Id
		List menuIds = baseDao.findBySQL("select 'menu' as type , menu_Id as id from sys_role_menu where role_Id = ?", Transformers.ALIAS_TO_ENTITY_MAP, roleId);
		List operations = baseDao.findBySQL("select 'operation' as type , operation_Id as id from sys_role_operation where role_Id = ?", Transformers.ALIAS_TO_ENTITY_MAP, roleId);
		menuIds.addAll(operations);
		return menuIds;
	}

}
