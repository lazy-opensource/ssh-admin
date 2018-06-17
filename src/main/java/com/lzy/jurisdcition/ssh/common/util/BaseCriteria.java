package com.lzy.jurisdcition.ssh.common.util;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCriteria {

    protected List<Object> values = new ArrayList<Object>();
	
	public abstract String getCondition() ;
	
	
	public List<Object> getValues() {
		return values;
	}
	public void setValues(List<Object> values) {
		this.values = values;
	}
}
