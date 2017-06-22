package com.maraujo.requestproject.sync.result;

public class PaginatedResult<T> {

	private PageInfo pageInfo;

	private T result;

	public PaginatedResult() {
		super();
	}

	public PaginatedResult(PageInfo pageInfo, T result) {
		super();
		this.pageInfo = pageInfo;
		this.result = result;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
