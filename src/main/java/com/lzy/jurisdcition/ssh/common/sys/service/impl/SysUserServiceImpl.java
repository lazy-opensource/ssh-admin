package com.lzy.jurisdcition.ssh.common.sys.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzy.jurisdcition.ssh.common.base.service.BaseService;
import com.lzy.jurisdcition.ssh.common.sys.criteria.SysUserCriteria;
import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;
import com.lzy.jurisdcition.ssh.common.sys.service.SysUserService;
import com.lzy.jurisdcition.ssh.common.util.PageBean;

//@Transactional
@Service("sysUserService")
public class SysUserServiceImpl extends BaseService implements SysUserService{

	@SuppressWarnings("unchecked")
	public SysUser login(SysUser sysUser) {
		if(sysUser == null){
			return null;
		}
		// 用户状态：0启用；1禁用；2删除
		SysUser user = (SysUser)super.uniqueResult(baseDao.find("from SysUser where userName = ? and userPassword = ? and userStatus = ?", sysUser.getUserName(),sysUser.getUserPassword() , 0));
		if(user != null){
			//baseDao.initialize(user);
			baseDao.initializeDeep(user.getSysRoles());
		}
		return user;
	}

	public void save(SysUser sysuser) {
		baseDao.add(sysuser);
		
	}

	public Integer delete(String[] ids) {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		if(ids != null && ids.length > 0){
			for(String id : ids){
				sb.append(id+",");
			}
		}
		sb.deleteCharAt((sb.length()-1)).append("");
		return baseDao.deleteByValues(SysUser.class, "userId", sb.toString());
		
	}

	public void delete(Integer id) {
		baseDao.delete(SysUser.class, id);
		
	}

	public void update(SysUser sysuser) {
		baseDao.update(sysuser);
		
	}

	public SysUser get(int id) {
		SysUser sysUser =  baseDao.get(SysUser.class, id);
		baseDao.initializeDeep(sysUser.getSysRoles());
		return sysUser;
	}

	public void findByPage(PageBean<SysUser> pb, SysUserCriteria sysuser) {
		pb.setEntityName(" SysUser ");
		baseDao.findByPage(pb,sysuser);
		for(Object o : pb.getData()){
			baseDao.initializeDeep(((SysUser)o).getSysRoles());//消除懒加载异常   深度强制加载
		}
	}

	public void changePwd(int id, String pwd) {
		baseDao.updateByHql("update SysUser  set userPassword = ? where userId = ?", pwd , id);
		
	}

	public boolean existsName(String name) {
		//不支持模糊查询
		return baseDao.findCount("select count(*) from SysUser where userName = ?", name) > 0;
	}

	public boolean existsName(String name, Integer userId) {
		//不支持模糊查询
		return baseDao.findCount("select count(*) from SysUser where userName = ? and userId = ?" , name , userId) > 0;
	}

	public int findMaxPage(int rowsPerPage) {
		
		return (baseDao.findMaxPage("select count(*) from SysUser", rowsPerPage));
	}

	@SuppressWarnings("unchecked")
	public String getPwd(int id) {
		
		return super.uniqueResult(baseDao.find("select userPassword from SysUser where userId = ?", id)).toString();
	}

}
