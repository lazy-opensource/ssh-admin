package com.lzy.jurisdcition.ssh.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class EasyUITreeEntity {

	/**
	 *  id：节点ID，对加载远程数据很重要。
		text：显示节点文本。
		state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
		checked：表示该节点是否被选中。
		attributes: 被添加到节点的自定义属性。
		children: 一个节点数组声明了若干节点。

	 */
	private String id;  
	private String text;  
	private String url; 
	private String state;  
	private String iconCls;  
	private boolean checked=false;  
	private Map<String, Object> attributes=new HashMap<String, Object>(); 
	private List<EasyUITreeEntity> children=new ArrayList<EasyUITreeEntity>();
	private String remark; 
	private String type; 
	
	public static final String MENU = "menu";
	public static final String OPERATION = "operation";
	public EasyUITreeEntity() {
		super();
	}
	public EasyUITreeEntity(String id, String text, String url, String state, String iconCls, boolean checked,
			Map<String, Object> attributes, List<EasyUITreeEntity> children, String remark, String type) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.state = state;
		this.iconCls = iconCls;
		this.checked = checked;
		this.attributes = attributes;
		this.children = children;
		this.remark = remark;
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<EasyUITreeEntity> getChildren() {
		return children;
	}
	public void setChildren(List<EasyUITreeEntity> children) {
		this.children = children;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "EasyUITreeEntity [id=" + id + ", text=" + text + ", url=" + url + ", state=" + state + ", iconCls="
				+ iconCls + ", checked=" + checked + ", attributes=" + attributes + ", children=" + children
				+ ", remark=" + remark + ", type=" + type + "]";
	}
	
}
