package com.lzy.jurisdcition.ssh.common.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzy.jurisdcition.ssh.common.base.service.BaseService;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysOperation;
import com.lzy.jurisdcition.ssh.common.sys.service.SysOperationService;
import com.lzy.jurisdcition.ssh.common.util.BaseCriteria;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

//@Transactional
@Service("sysOperationService")
public class SysOperationServiceImpl extends BaseService implements SysOperationService {

	public void add(SysOperation sysOperation) {
		baseDao.add(sysOperation);
	}

	public Integer delete(String ids) {
        return baseDao.deleteByValues(SysOperation.class, "operationId", ids);
	}

	public void deleteByMenuPermissionId(int menuPermissionId) {
        baseDao.updateByHql("delete from SysOperation where MenuId = ?", menuPermissionId);
	}

	public void delete(SysOperation sysOperation) {
		baseDao.delete(SysOperation.class, sysOperation);

	}

	public void update(SysOperation sysOperation) {
		baseDao.update(sysOperation);

	}

	public SysOperation get(int id) {
		
		return baseDao.get(SysOperation.class, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> list(Integer menuId) {
		
		return baseDao.findByCache("select new map(s.operationName as operationName,s.operationAction as operationAction,s.operationRemark as operationRemark) from SysOperation as s where sysMenu.menuId = ?", menuId);
	}

	@SuppressWarnings("unchecked")
	public List<String> getIdsByRoleId(Integer roleId) {
		
		return baseDao.findBySQL("select operation_Id from sys_role_operation where role_Id = ?", roleId);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getAllOpreationNames() {
		Map<String,String> operationsName = new HashMap<String,String>();
		
		List<Map<String,String>> menuList = baseDao.find("select new map(menuName as name ,menuAction as action , menuRemark as remark) from SysMenu ");
		List<Map<String,String>> operationList = baseDao.find("select new map(operationName as name , operationAction as action , operationRemark as remark )from SysOperation");
		menuList.addAll(operationList);
		String operationName = null;
		for(Map<String,String> map : menuList){
			if(isNotNullAndEmpty(map.get("action"))){
				for(String str : map.get("action").split("#|,")){
				     str = str.trim();
				     if(str.length() > 0){
				    	 String name = map.get("name");
				    	 String remark = map.get("remark");
				    	 operationName = name;
				    	 if(isNotNullAndEmpty(remark)){
				    		 operationName += "("+remark+")";
				    	 }
				    	 operationsName.put(str, operationName);
				     }
				}
			}
		}
		return operationsName;
	}

	public void findByPage(PageBean<SysOperation> pageBean, BaseCriteria baseCriteria) {
		pageBean.setEntityName("SysOperation");
		baseDao.findByPage(pageBean, baseCriteria);
		
	}

    public List<SysOperation> findAll() {
        return baseDao.findAll(SysOperation.class);
    }
}
