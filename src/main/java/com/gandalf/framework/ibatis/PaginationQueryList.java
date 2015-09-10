package com.gandalf.framework.ibatis;

import java.util.ArrayList;
import java.util.Collection;

public class PaginationQueryList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 8761383695528059074L;
	private BaseQuery query;

	public PaginationQueryList() {
		super();
		this.query = new BaseQuery();
	}

	public PaginationQueryList(BaseQuery query) {
		super();
		this.query = query;
	}

	public PaginationQueryList(Collection c) {
		super(c);
	}

	public PaginationQueryList(Collection c, BaseQuery query) {
		super(c);
		this.query = query;
	}

	public BaseQuery getQuery() {
		return query;
	}

	public void setQuery(BaseQuery query) {
		this.query = query;
	}

	public int getTotalItem() {
		return query.getTotalItem() != null ? query.getTotalItem().intValue() : 0;
	}

	public void setTotalItem(int totalItem) {
		query.setTotalItem(new Integer(totalItem));
	}

	public int getTotalPage() {
		return query.getTotalPage();
	}

	public int getPageSize() {
		return query.getPageSize() != null ? query.getPageSize().intValue() : 0;
	}
}
