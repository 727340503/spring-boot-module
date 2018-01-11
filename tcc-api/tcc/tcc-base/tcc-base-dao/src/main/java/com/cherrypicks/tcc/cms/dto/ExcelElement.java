package com.cherrypicks.tcc.cms.dto;

public class ExcelElement {
    private String title;
    private String field;
    private Boolean isCount;
    private Integer width;

    public ExcelElement() {

    }

    public ExcelElement(final String title, final String field, final Boolean isCount, final Integer width) {
        this.title = title;
        this.field = field;
        this.isCount = isCount;
        this.width = width;
    }

    public Boolean getIsCount() {
        return isCount;
    }

    public void setIsCount(final Boolean isCount) {
        this.isCount = isCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(final Integer width) {
        this.width = width;
    }
}
