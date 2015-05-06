//--------------------------------------------------------------------------
//	Copyright (c) 2010-2020, En.ennisit or Cn.pudongping
//  All rights reserved.
//
//	Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions are
//  met:
//
//	Redistributions of source code must retain the above copyright notice,
//  this list of conditions and the following disclaimer.
//	Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//	Neither the name of the Dennisit nor the names of its contributors
//  may be used to endorse or promote products derived from this software
//  without specific prior written permission.
//  Author: dennisit@163.com
//--------------------------------------------------------------------------
package com.plugin.server.reduce.data;
import java.io.Serializable;
import java.util.List;
/**
 * This is a basic Paging component.
 * @author dennisit@163.com
 * @param <T>
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * data row total
	 */
	private int recordTotal;

	/**
	 * each page data number 
	 */
	private int pageSize;

	/**
	 * current page number
	 */
	private int pageNum;

	/**
	 * page data package is list collection
	 */
	private List<T> items = null;

	/**
	 * all page numbers 
	 */
	private int pageTotal;

	/**
	 * current page is first page or not
	 */
	private boolean isFirstPage = false;

	/** 
	 * current page is last page or not
	 */
	private boolean isLastPage = false;

	/**
	 * default page number is size 10
	 */
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * construction with parameters
	 * @param list
	 * @param recordTotal
	 * @param pageNum
	 */
	public Page(List<T> list, int recordTotal, int pageNum) {
		setPage(list, recordTotal, pageNum, DEFAULT_PAGE_SIZE);
	}
	

	/**
	 * construction with parameters
	 * @param list
	 * @param recordTotal
	 * @param pageNum
	 * @param pageSize
	 */
	public Page(List<T> list, int recordTotal, int pageNum, int pageSize) {
		setPage(list, recordTotal, pageNum, pageSize);
	}

	/**
	 * The core operation
	 * @param list
	 * @param recordTotal
	 * @param pageNum
	 * @param pageSize
	 */
	private void setPage(List<T> list, int recordTotal, int pageNum,int pageSize) {
		int pageCount = 0;
		if (recordTotal % pageSize == 0) {
			pageCount = recordTotal / pageSize;
		} else {
			pageCount = recordTotal / pageSize + 1;
		}
		if (pageNum == 1) {
			setFirstPage(true);
		}
		if (pageNum == pageCount) {
			setLastPage(true);
		}
		this.items = list;
		this.pageTotal = pageCount;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.recordTotal = recordTotal;

	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}	

	public int getTotalPage() {
		if (this.getPageSize() > 0) {
			return ((recordTotal - 1) / this.getPageSize() + 1);
		}
		return 0;
	}

	public boolean isNextPage() {
		return ((this.pageTotal > 1) && (this.pageNum < this.pageTotal));
	}

	public boolean isPrePage() {
		return ((this.pageTotal > 1) && (this.pageNum < this.pageTotal));
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	@Override
	public String toString() {
		return "Page[recordTotal=" + this.recordTotal + ",pageSize="
				+ this.pageSize + ",pageTotal=" + this.pageTotal + ",pageNum="
				+ this.pageNum + ",isFirstPage=" + this.isFirstPage
				+ ",isLastPage=" + this.isLastPage + ",page.items.length="
				+ this.getItems().size() + "]";
	}

}

