package com.gandalf.framework.web.tool;

import java.util.List;

/**
 * 该分页适用于mysql数据库
 * @author gandalf
 *
 * @param <E>
 */
public class Page<E> {
	
	private int curPage = 0;
	private int pageSize = 10;
	private int totalPages = 0;
	private int totalCounts = 0;
	private int offset = 0;
	private List<E> records;
	
	public Page(){
		setOffset();
	}
	
	public Page(int curPage){
		this.curPage = curPage;
		setOffset();
	}
	
	public Page(int curPage, int pageSize){
		this.curPage = curPage;
		this.pageSize = pageSize;
		setOffset();
	}
	
	public int getCurPage() {
		return curPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}
	
	private void setTotalPages() {
		this.totalPages = (totalCounts + pageSize -1) / pageSize;
	}
	
	public int getTotalCounts() {
		return totalCounts;
	}
	
	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
		setTotalPages();
	}
	
	public int getOffset() {
		return offset;
	}

	private void setOffset() {
		this.offset = (curPage-1) * pageSize;
	}

	public List<E> getRecords() {
		return records;
	}

	public void setRecords(List<E> records) {
		this.records = records;
	}
	
}