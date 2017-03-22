package com.zor07.domain;

/**
 * Created by anzor on 22.03.17.
 */
public class SearchCriteria {

    private String dateFromFilter;
    private String dateToFilter;
    private String sourceFilter;
    private String typeFilter;
    private String categoryFilter;
    private String amountFilter;

    public String getDateFromFilter() {
        return dateFromFilter;
    }

    public void setDateFromFilter(String dateFromFilter) {
        this.dateFromFilter = dateFromFilter;
    }

    public String getDateToFilter() {
        return dateToFilter;
    }

    public void setDateToFilter(String dateToFilter) {
        this.dateToFilter = dateToFilter;
    }

    public String getSourceFilter() {
        return sourceFilter;
    }

    public void setSourceFilter(String sourceFilter) {
        this.sourceFilter = sourceFilter;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(String typeFilter) {
        this.typeFilter = typeFilter;
    }

    public String getCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(String categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public String getAmountFilter() {
        return amountFilter;
    }

    public void setAmountFilter(String amountFilter) {
        this.amountFilter = amountFilter;
    }
}
