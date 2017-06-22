package com.maraujo.requestproject.sync.result;

public class PageInfo {

	private long totalItems;

	private int currentPage;

	public PageInfo(long totalItems, int currentPage) {
		super();
		this.totalItems = totalItems;
		this.currentPage = currentPage;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
