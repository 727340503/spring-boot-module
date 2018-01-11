package com.cherrypicks.tcc.model;

public class Category extends BaseModel {

	private static final long serialVersionUID = -7121608915780256900L;

	private Long parentId;
	private Integer sortOrder;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}
