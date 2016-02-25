package com.damselfly.viewModel;

/**
 * Created by v on 2014/8/14.
 */
public class Table {
    private String head;//中文列名
    private String columns;//编码
    private String url;//请求链接

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
