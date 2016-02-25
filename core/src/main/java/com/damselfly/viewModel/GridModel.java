package com.damselfly.viewModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class GridModel {
	private List  rows= new ArrayList();
	private Integer total=0;
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
