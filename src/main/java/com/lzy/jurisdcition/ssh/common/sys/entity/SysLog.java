package com.lzy.jurisdcition.ssh.common.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer logId;
	private String logAction;
	private String logParameters;
	private String logRes;
	private String logAccount;
	private String logIp;
	private Date logTime;
	public SysLog() {
		super();
	}
	public SysLog(Integer logId, String logAction, String logParameters, String logRes, String logAccount, String logIp,
			Date logTime) {
		super();
		this.logId = logId;
		this.logAction = logAction;
		this.logParameters = logParameters;
		this.logRes = logRes;
		this.logAccount = logAccount;
		this.logIp = logIp;
		this.logTime = logTime;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getLogAction() {
		return logAction;
	}
	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}
	public String getLogParameters() {
		return logParameters;
	}
	public void setLogParameters(String logParameters) {
		this.logParameters = logParameters;
	}
	public String getLogRes() {
		return logRes;
	}
	public void setLogRes(String logRes) {
		this.logRes = logRes;
	}
	public String getLogAccount() {
		return logAccount;
	}
	public void setLogAccount(String logAccount) {
		this.logAccount = logAccount;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	@Override
	public String toString() {
		return "SysLog [logId=" + logId + ", logAction=" + logAction + ", logParameters=" + logParameters + ", logRes="
				+ logRes + ", logAccount=" + logAccount + ", logIp=" + logIp + ", logTime=" + logTime + "]";
	}
	
}
