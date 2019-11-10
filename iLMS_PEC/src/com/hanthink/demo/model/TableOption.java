package com.hanthink.demo.model;

import java.util.List;

/**
 * CRUD表格Option设置
 * <p>Title: TableOption</p>
 * <p>Description:</p>
 * @author whh
 * @date 2018年11月1日
 * @version 1.0
 */
public class TableOption {

	/**
	 * 是否复选
	 */
	private boolean selection = false;

	/**
	 * 是否设置边框
	 */
	private boolean border = true;

	/**
	 * 是否添加序号列
	 */
	private boolean index = false;

	/**
	 * 是否显示查看按钮
	 */
	private boolean viewBtn = false;

	/**
	 * 是否显示删除按钮
	 */
	private boolean delBtn = false;

	/**
	 * 是否显示添加按钮
	 */
	private boolean addBtn = false;

	/**
	 * 是否显示编辑按钮
	 */
	private boolean editBtn = false;

	/**
	 * 是否显示按钮
	 */
	private boolean menu = false;

	/**
	 * 表格的居中方式
	 */
	private String align;

	/**
	 * 是否显示分页
	 */
	private boolean page = false;

	/**
	 * 是否显示搜索按钮
	 */
	private boolean searchBtn = false;

	/**
	 * 是否显示列显隐按钮
	 */
	private boolean columnBtn = false;

	/**
	 * 是否显示刷新按钮
	 */
	private boolean refreshBtn = false;

	/**
	 * 表单的宽
	 */
	private String formWidth = "60%";

	/**
	 * 序号列的名称
	 */
	private String indexLabel = "No";

	/**
	 * 列明细Option
	 */
	private List<ColumnOption> column;

	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public boolean isViewBtn() {
		return viewBtn;
	}

	public void setViewBtn(boolean viewBtn) {
		this.viewBtn = viewBtn;
	}

	public boolean isDelBtn() {
		return delBtn;
	}

	public void setDelBtn(boolean delBtn) {
		this.delBtn = delBtn;
	}

	public boolean isAddBtn() {
		return addBtn;
	}

	public void setAddBtn(boolean addBtn) {
		this.addBtn = addBtn;
	}

	public boolean isEditBtn() {
		return editBtn;
	}

	public void setEditBtn(boolean editBtn) {
		this.editBtn = editBtn;
	}

	public boolean isMenu() {
		return menu;
	}

	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public boolean isSearchBtn() {
		return searchBtn;
	}

	public void setSearchBtn(boolean searchBtn) {
		this.searchBtn = searchBtn;
	}

	public boolean isColumnBtn() {
		return columnBtn;
	}

	public void setColumnBtn(boolean columnBtn) {
		this.columnBtn = columnBtn;
	}

	public boolean isRefreshBtn() {
		return refreshBtn;
	}

	public void setRefreshBtn(boolean refreshBtn) {
		this.refreshBtn = refreshBtn;
	}

	public String getFormWidth() {
		return formWidth;
	}

	public void setFormWidth(String formWidth) {
		this.formWidth = formWidth;
	}

	public String getIndexLabel() {
		return indexLabel;
	}

	public void setIndexLabel(String indexLabel) {
		this.indexLabel = indexLabel;
	}

	public List<ColumnOption> getColumn() {
		return column;
	}

	public void setColumn(List<ColumnOption> column) {
		this.column = column;
	}

}
