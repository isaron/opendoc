package com.cloud.platform;

public class SearchVo {

	/**
	 * page
	 */
	private int page = 1;
	private int pageSize = 15;
	private int pageNum = 1;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
