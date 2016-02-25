package com.damselfly.entity;

import java.io.Serializable;

public class SecurityPortal implements Serializable {
    private Integer id;

    private String title;

    private String linkUrl;

    private String jsonUrl;

    private Integer urlType;

    private Integer showType;

    private Integer state;

    private String tableHead;

    private Integer indexShow;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl == null ? null : jsonUrl.trim();
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTableHead() {
        return tableHead;
    }

    public void setTableHead(String tableHead) {
        this.tableHead = tableHead == null ? null : tableHead.trim();
    }

    public Integer getIndexShow() {
        return indexShow;
    }

    public void setIndexShow(Integer indexShow) {
        this.indexShow = indexShow;
    }
}