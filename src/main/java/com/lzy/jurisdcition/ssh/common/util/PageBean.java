package com.lzy.jurisdcition.ssh.common.util;

import java.util.List;

import com.lzy.jurisdcition.ssh.common.sys.entity.SysUser;

public class PageBean<T> {

	
	private List<T> rows;
	
	private int pages;
	
	private int total;
	
	private String page;
	/**
	 *
	 * 前置条件， 可写 主要用于查询组合列，以select开头，如select new User(u.name,u.sex)
	 */
	private String select = "";
	/**
	 * 当前是第几页，可写
	 */
	private int pageNo = 1;
	/**
	 * 一共有多少页，可读
	 */
	private int pageTotal;
	/**
	 * 一共有多少行，可读
	 */
	private int rowsCount;
	/**
	 * 每页多少行，可写
	 */
	private int rowsPerPage = 10;
	/**
	 * 本页中显示的数据集合，可读
	 */
	@SuppressWarnings("rawtypes")
	private List data;
	/**
	 * 排序方式，可写
	 */
	private String order = "asc";
	/**
	 * 排序字段，可写，必须
	 */
	private String sort = "";
	/**
	 * 要查询的实体名，可写，必须
	 */
	private String entityName = "";
	/**
	 * 查询条件，无需使用where开始，，可写
	 */
	private String condition = "";

	/**
	 * hql语句最后的附加排序，可写 例如语句后的第二个排序条件，hql+",time desc"
	 */
	private String lastSort = "";
	/**
	 * 直接指定分页HQL语句，覆盖getAutoHql()自动生成的HQL语句，可写
	 */
	private String hql;
	/**
	 * 要排除的行数，内部使用
	 */
	private int rowStart;
	

	/**
	 * 分页查询条件对象，如果设置了condition，会被覆盖
	 */
	BaseCriteria baseCriteria;

	public String getAutoHql() {
		String hql = select + " from " + entityName + " where 1=1 ";
		if (isNotNullOrEmpty(condition)) {
			hql += condition;
		}
		if (isNotNullOrEmpty(sort)) {
			hql += " order by " + sort;
			if (isNotNullOrEmpty(order)) {
				hql += " " + order;
			}
		}

		if (isNotNullOrEmpty(sort)) {
			if (isNotNullOrEmpty(lastSort)
					&& lastSort.trim().length() != 0) {
				hql += "," + lastSort;
			}
		} else {
			if (isNotNullOrEmpty(lastSort)
					&& lastSort.trim().length() != 0) {
				hql += " order by " + lastSort;
			}
		}

		return hql;
	}

	public  boolean isNotNullOrEmpty(String s) {
		if (null == s || s.trim().equals("")) {
			return false;
		}
		return true;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 
	 */
	public PageBean() {
		super();
	}

	/**
	 * @return the curPage
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param curPage
	 *            the curPage to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo < 1 ? 1 : pageNo;
	}

	/**
	 * 得到总共有多少页
	 * 
	 * @return the maxPage
	 */
	public int getPageTotal() {
		/**
		 * 规律算法
		 */
		return pageTotal = (this.rowsCount - 1) / this.getRowsPerPage() + 1;
	}

	/**
	 * @param maxPage
	 *            the maxPage to set
	 */
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	/**
	 * @return the maxRow
	 */
	public int getRowsCount() {
		return rowsCount;
	}

	/**
	 * @param maxRow
	 *            the maxRow to set
	 */
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * 得到每页显示多少条记录
	 * 
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage <= 0 ? 5 : rowsPerPage;
	}

	/**
	 * @param rowsPerPage
	 *            the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @return the data
	 */
	@SuppressWarnings("rawtypes")
	public List getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	@SuppressWarnings("rawtypes")
	public void setData(List data) {
		this.data = data;
	}

	/**
	 * 得到分页前要排除的行数
	 * 
	 * @return the rowStart
	 */
	public int getRowStart() {
		int ret = (this.pageNo - 1) * this.getRowsPerPage();
		return ret < 1 ? 0 : ret;
	}

	/**
	 * @param rowStart
	 *            the rowStart to set
	 */
	@SuppressWarnings("unused")
	private void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}


	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getLastSort() {
		return lastSort;
	}

	public void setLastSort(String lastSort) {
		this.lastSort = lastSort;
	}

	public String getHql() {
		return hql;
	}
	

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> list) {
		this.rows = list;
	}
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
    
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "PageBean [select=" + select + ", pageNo=" + pageNo + ", pageTotal=" + pageTotal + ", rowsCount="
				+ rowsCount + ", rowsPerPage=" + rowsPerPage + ", data=" + data + ", order=" + order + ", sort=" + sort
				+ ", entityName=" + entityName + ", condition=" + condition + ", lastSort=" + lastSort + ", hql=" + hql
				+ ", rowStart=" + rowStart + ", baseCriteria=" + baseCriteria + "]";
	}
}
