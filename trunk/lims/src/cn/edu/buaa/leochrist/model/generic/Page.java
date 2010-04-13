package cn.edu.buaa.leochrist.model.generic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Page<T> {
	/**
	 * 当前页数 database
	 */
	private int index = 0;

	private int currentPage = 1;

	/**
	 * 每页最多显示数据条数
	 */
	private int size = DEFAULT_SIZE;

	public static final int DEFAULT_SIZE = 25;

	/**
	 * 把结果显示在一页上
	 */
	public static final int ALL = 10000;

	/**
	 * 排序指令 多个以‘,’隔开 如 "name desc, birthday asc"
	 */
	private String orders;

	/**
	 * 总页面数
	 */
	private int totalPageNum;

	/**
	 * 记录总数
	 */
	private long recordNum;

	/**
	 * 该页的数据
	 */
	private List<T> results;

	private Iterator<T> iterator;
	
	private T uniqueResult;
	
	/**
	 * 排序指令 多个以‘,’隔开 如 "name desc, birthday asc"
	 */
	private String groupby;
	

	public Page() {

	}

	/**
	 * 是否有下一页
	 *
	 * @return
	 */
	public boolean hasNext() {
		return getTotalPageNum() - this.getCurrentPage() > 0;
	}

	/**
	 * 是否有前一页
	 *
	 * @return
	 */
	public boolean hasPre() {
		return (this.getTotalPageNum() > 1 && this.getCurrentPage() > 1);
	}

	/**
	 * 得到下一页的页数 如果没有下一页 返回0
	 *
	 * @return
	 */
	public int getNext() {
		if (hasNext()) {
			return this.getCurrentPage() + 1;
		} else
			return getCurrentPage();
	}

	/**
	 * 得到前一页的页数 如果没有前一页 返回0
	 *
	 * @return
	 */
	public int getPre() {
		if (hasPre()) {
			return getCurrentPage() - 1;
		} else
			return getCurrentPage();
	}

	/**
	 * 返回总页数
	 *
	 * @return
	 */
	public int getTotalPageNum() {
		totalPageNum = (int) ((recordNum + size - 1) / size);

		return totalPageNum == 0 ? 1 : totalPageNum;
	}

	public int getIndex() {

		this.index = this.getCurrentPage() - 1;

		if (this.index < 0) {
			this.index = 0;
		}
		return index;
	}

	public int getSize() {

		if (this.size <= 0) {
			this.size = DEFAULT_SIZE;

		}

		return size;
	}

	public void setSize(int size) {
		this.size = size;
		if (this.size <= 0) {
			this.size = DEFAULT_SIZE;

		}
	}

	public long getResultFrom() {
		return this.getIndex() * this.getSize() + 1;
	}

	public long getResultTo() {
		long result=this.getCurrentPage() * this.getSize();
		if (result>getRecordNum())
			 result=getRecordNum();
		return result;
	}

	public long getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(long recordNum) {
		this.recordNum = recordNum;

		if (this.recordNum < 0)
			this.recordNum = 0;
	}

	/**
	 * 得到当前页的数据
	 *
	 * @return
	 */
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public List<T> getResults() {

		if (results == null || results.isEmpty()) {

			if (iterator != null) {

				while (iterator.hasNext()) {

					if (results == null) {
						results = new ArrayList<T>();
					}

					T element = iterator.next();
					results.add(element);
				}
			}
		}
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getCurrentPage() {

		if (currentPage < 1) {
			currentPage = 1;
		}

		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			currentPage = 1;
		}

		this.currentPage = currentPage;

	}

	/**
	 * 分页展示代码
	 * @param url
	 * @return
	 */
	public String getMulti(String url) {
		String multipage = "";
		int page = 5;
		int offset = 2;
		int from = 1, to = 1;
		
		url += url.indexOf('?') == -1 ? "?" : "&";
		int pages = getTotalPageNum();
		
		if (pages > 1) {
			if (page > pages) {
				from = 1;
				to = pages;
			} else {
				from = getCurrentPage() - offset;
				to = from + page - 1;
				if (from < 1) {
					to = getCurrentPage() + 1 - from;
					from = 1;
					if (to - from < page)
						to = page;
				} else if (to > pages) {
					from = pages - page + 1;
					to = pages;
				}
			}
			multipage = (getCurrentPage() - offset > 1 && pages > page ? "<a href=\""
					+ url + "page=1\" class=\"first\">1 ...</a>" : "")
					+ (getCurrentPage() > 1 ? "<a href=\"" + url 
					+ "page=" + (getCurrentPage() - 1) 
					+ "\" class=\"prev\">&lsaquo;&lsaquo;</a>" : "");
			for (int i = from; i <= to; i++) {
				multipage += i == getCurrentPage() ? "<strong>" + i + "</strong>" :
					"<a href=\"" + url + "page=" + i + "\">" + i + "</a>";
			}
			multipage += (getCurrentPage() < pages ? "<a href=\"" + url 
					+ "page=" + (getCurrentPage() + 1) 
					+ "\" class=\"next\">&rsaquo;&rsaquo;</a>" : "")
					+ (to < pages ? "<a href=\"" + url + "page=" + pages
					+ "\" class=\"last\">... " + pages + "</a>" : "");
			if (!StringUtils.isBlank(multipage)) 
				multipage = "<em>&nbsp;" + getRecordNum() + "&nbsp;</em>" + multipage;
		}
		return multipage;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		Page page = new Page();
		page.setRecordNum(100);

		page.setCurrentPage(3);


	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	
	public String[] getOrdersArray() {
		if(orders==null){
			return null;
		}else{
			String [] orderArray = orders.split(",");
			return orderArray;
		} 
	}
	
	public Iterator<T> getIterator() {
		return iterator;
	}

	public void setIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	public T getUniqueResult() {
		 
		return uniqueResult;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

}
