package com.damselfly.entity;

import java.io.Serializable;

public class SecurityPortalReport implements Serializable {
    private Integer id;

    private String title;

    private Double colValue;

    private Double colValueSecond;

    public Double getColValueSecond() {
        return colValueSecond;
    }

    public void setColValueSecond(Double colValueSecond) {
        this.colValueSecond = colValueSecond;
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

    public Double getColValue() {
        return colValue;
    }

    public void setColValue(Double colValue) {
        this.colValue = colValue;
    }
}