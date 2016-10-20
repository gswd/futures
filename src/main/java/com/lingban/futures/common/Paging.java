package com.lingban.futures.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 分页类
 */
public class Paging<T> implements Serializable {

	private static final long serialVersionUID = -7078903605294496340L;
	
	/** ================================= 查询条件 开始 ================================== */
	/** 页码，从1开始 */
	private int pageNum = 1;
	/** 页面大小 */
	private int pageSize = 10;
	/** ================================= 查询条件 结束 ================================== */

	/** ================================= 以下均为展示的结果 ================================== */
	/** 当前页的数量，有可能当前页不足分页的数量 */
	private int size;

	/** 起始行 */
	private int startRow;
	/** 末行 */
	private int endRow;

	/** 总记录数 */
	private long total;
	/** 总页数 */
	private int pages;

	/** 排序 */
	private String orderBy;

	/** 结果集 */
	private List<T> list;

	/** 第一页 */
	private int firstPage;
	/** 前一页 */
	private int prePage;
	/** 下一页 */
	private int nextPage;
	/** 最后一页 */
	private int lastPage;

	/** 是否为第一页 */
	private boolean isFirstPage = false;
	/** 是否为最后一页 */
	private boolean isLastPage = false;
	/** 是否有前一页 */
	private boolean hasPreviousPage = false;
	/** 是否有下一页 */
	private boolean hasNextPage = false;

	/** 导航页码数 */
	private int navigatePages = 8;
	/** 所有导航页号 */
	private int[] navigatepageNums;

	public Paging() {
		super();
	}

	public Paging(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 */
	public Paging(List<T> list) {
		this(list, 8);
	}

	/**
	 * 包装Page对象
	 *
	 * @param list          page结果
	 * @param navigatePages 页码数量
	 */
	public Paging(List<T> list, int navigatePages) {
		setList(list);
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatepageNums() {
		//当总页数小于或等于导航页码数时
		if (pages <= navigatePages) {
			navigatepageNums = new int[pages];
			for (int i = 0; i < pages; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else { //当总页数大于导航页码数时
			navigatepageNums = new int[navigatePages];
			int startNum = pageNum - navigatePages / 2;
			int endNum = pageNum + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				//(最前navigatePages页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > pages) {
				endNum = pages;
				//最后navigatePages页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				//所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 计算前后页，第一页，最后一页
	 */
	private void calcPage() {
		if (navigatepageNums != null && navigatepageNums.length > 0) {
			firstPage = navigatepageNums[0];
			lastPage = navigatepageNums[navigatepageNums.length - 1];
			if (pageNum > 1) {
				prePage = pageNum - 1;
			}
			if (pageNum < pages) {
				nextPage = pageNum + 1;
			}
		}
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNum == 1;
		isLastPage = pageNum == pages;
		hasPreviousPage = pageNum > 1;
		hasNextPage = pageNum < pages;
	}

	/**
	 * 计算起止行号
	 */
	private void calculateStartAndEndRow() {
		this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
		this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
	}

	public int getPageNum() {
		return pageNum;
	}

	public Paging<T> setPageNum(int pageNum) {
		//分页合理化，针对不合理的页码自动处理
		this.pageNum = pageNum <= 0 ? 1 : pageNum;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Paging<T> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		calculateStartAndEndRow();
		return this;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		if (total == -1) {
			pages = 1;
			return;
		}
		if (pageSize > 0) {
			pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
		} else {
			pages = 0;
		}
		//分页合理化，针对不合理的页码自动处理
		if (pageNum > pages) {
			pageNum = pages;
			calculateStartAndEndRow();
		}
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		if (list instanceof Page) {
			
			Page<T> page = (Page<T>) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.orderBy = page.getOrderBy();

			this.pages = page.getPages();
			this.list = page;
			this.size = page.size();
			this.total = page.getTotal();
			//由于结果是>startRow的，所以实际的需要+1
			if (this.size == 0) {
				this.startRow = 0;
				this.endRow = 0;
			} else {
				this.startRow = page.getStartRow() + 1;
				//计算实际的endRow（最后一页的时候特殊）
				this.endRow = this.startRow - 1 + this.size;
			}
		} else if (list instanceof Collection) {
			this.pageNum = 1;
			this.pageSize = list.size();

			this.pages = 1;
			this.list = list;
			this.size = list.size();
			this.total = list.size();
			this.startRow = 0;
			this.endRow = list.size() > 0 ? list.size() - 1 : 0;
		}
		if (list instanceof Collection) {
			//计算导航页
			calcNavigatepageNums();
			//计算前后页，第一页，最后一页
			calcPage();
			//判断页面边界
			judgePageBoudary();
		}
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean firstPage) {
		isFirstPage = firstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean lastPage) {
		isLastPage = lastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}
}
