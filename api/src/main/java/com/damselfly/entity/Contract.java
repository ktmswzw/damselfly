package com.damselfly.entity;

import java.io.Serializable;
import java.util.Date;

public class Contract implements Serializable {
    private Integer contract_id;

    private Integer custId;

    private String title;

    private String content;

    private String contract_code;

    private Date signDate;

    private Date startDate;

    private Date endDate;

    private Date state_date;

    private Integer state;

    private String signName;

    private Integer signUserId;

    public Integer getContract_id() {
        return contract_id;
    }

    public void setContract_id(Integer contract_id) {
        this.contract_id = contract_id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code == null ? null : contract_code.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getState_date() {
        return state_date;
    }

    public void setState_date(Date state_date) {
        this.state_date = state_date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    public Integer getSignUserId() {
        return signUserId;
    }

    public void setSignUserId(Integer signUserId) {
        this.signUserId = signUserId;
    }
}