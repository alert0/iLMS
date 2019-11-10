package com.hotent.base.db.mybatis.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 包含“分页”信息的List，这个对象包含分页数据和分页结果。
 * 
 * <pre>
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-1-3-下午3:59:14
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class PageList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1412759446332294208L;

	private PageResult pageResult;

	public PageList() {
	}

	public PageList(Collection<? extends E> c) {
		super(c);
		if (c instanceof PageList<?>) {
			this.pageResult = ((PageList<?>) c).pageResult;
		}
	}

	public PageList(Collection<? extends E> c, PageResult p) {
		super(c);
		this.pageResult = p;
	}

	public PageList(PageResult p) {
		this.pageResult = p;
	}

	/**
	 * 得到分页器，通过Paginator可以得到总页数等值
	 * 
	 * @return
	 */
	public PageResult getPageResult() {
		return pageResult;
	}

	/**
	 * @param pageResult
	 *            the pageResult to set
	 */
	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	/**
	 * <pre>
	 * 返回成jqgrid 需求的返回的map
	 * rows:数据
	 * records:总数据数
	 * page:当前页
	 * total:总页数
	 * 注意：这些名称其实可以jqgrid页面配置成其他名称，我们系统就用其默认名称
	 * </pre>
	 * @return jqgrid Map<String,Object>
	 * @exception
	 * @since 1.0.0
	 */
	public Map<String, Object> toJqGridMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", this);
		map.put("records", this.getPageResult().getTotalCount());
		map.put("page", this.getPageResult().getPage());
		map.put("total", this.getPageResult().getTotalPages());
		return map;
	}

}
