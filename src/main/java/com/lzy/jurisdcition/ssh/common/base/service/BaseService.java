package com.lzy.jurisdcition.ssh.common.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lzy.jurisdcition.ssh.common.base.dao.BaseDao;

@Service
public class BaseService {

	@Resource(name = "baseDao")
	protected BaseDao baseDao;
	
	
	public <T> T uniqueResult(List<T> list){
		return ((list==null||list.size()==0)?null:(T)list.get(0));
	}
	
	public boolean isNotNullAndEmpty(Object str){
		return str!=null&&(!str.equals(""));
	}
}
