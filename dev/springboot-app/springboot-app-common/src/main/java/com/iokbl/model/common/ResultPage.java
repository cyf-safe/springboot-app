package com.iokbl.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @desc 列表分页类
 * @author chenyufei
 * @date 2018年10月24日17:38:58
 * @param <T>
 */
public class ResultPage<T> implements Serializable {

	private static final long serialVersionUID = -5232984286074631656L;
	
	public int currentPage = 1;
	
	public int pageSize = 10;
	
	public long count;
	
	public int pages;
	
	public int start = 0;
	
	public int end = 10;
	
	public List<T> list = new ArrayList<>();

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
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
