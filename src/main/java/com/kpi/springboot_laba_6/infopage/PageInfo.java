package com.kpi.springboot_laba_6.infopage;


import java.util.List;

public class PageInfo<T> {
    private List<T> obj;
    private int totalPages;

    private int totalRecords;
    private int recordsPerPage;

    public PageInfo(List<T> obj, int totalPages, long totalRecords, int recordsPerPage) {
        this.obj = obj;
        this.totalPages = totalPages;
        this.totalRecords = (int)totalRecords;
        this.recordsPerPage = recordsPerPage;
    }

    public PageInfo(){}

    public List<T> getObj() {
        return obj;
    }

    public void setObj(List<T> obj) {
        this.obj = obj;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }
}
