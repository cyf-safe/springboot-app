package com.iokbl.model.common;
/**
 * @desc 分页参数类
 * @author chenyufei
 * @2020年02月26日17:50:09
 */
public class PagePara {
	
	public int currentPage = 1;
	
	public int pageSize = 10;
	
	public long count;
	
	public int pages;
	
	public int start = 0;
	
	public int end = 10;

	public int getCurrentPage() {
		return currentPage == 0?1:currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize == 0?10:pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
		if(count > 0) {
			this.pages = (int) this.count / this.pageSize;
			if(this.count % this.pageSize > 0) {
				pages++;
			}
			
			if(this.currentPage > this.pages) {
				this.currentPage = this.pages;
			}
			
			this.start = (this.currentPage - 1) * this.pageSize;
			this.end = this.start + this.pageSize;
		}
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
}
