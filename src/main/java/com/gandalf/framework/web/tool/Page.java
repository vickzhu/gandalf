package com.gandalf.framework.web.tool;

import java.io.Serializable;
import java.util.List;

/**
 * 该分页适用于mysql数据库
 * @author gandalf
 *
 * @param <E>
 */
public class Page<M> implements Serializable {

	private static final long serialVersionUID = 6358744597666526022L;
	
	private int curPage = 1;
	private int pageSize = 10;
	private int totalPages = 0;
	private int totalCounts = 0;
	private int offset = 0;
	private List<M> records;
	
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
	
	public void setCurPage(int curPage) {
		this.curPage = curPage;
		setOffset();
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

	public List<M> getRecords() {
		return records;
	}

	public void setRecords(List<M> records) {
		this.records = records;
	}
	
}
