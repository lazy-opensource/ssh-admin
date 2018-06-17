package com.lzy.jurisdcition.ssh.common.util;

public class MyJson {

	private String msg;
	private int statusCode = StatusCode.OK;
	private Object data;
	public MyJson() {
		super();
	}
	public MyJson(String msg, int statusCode, Object data) {
		super();
		this.msg = msg;
		this.statusCode = statusCode;
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "MyJson [msg=" + msg + ", statusCode=" + statusCode + ", data=" + data + "]";
	}
	
	
	
}
