package com.damselfly.business.entity;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class Opportunities implements Serializable {
    private Integer opportunitiesId;

    private String email;

    private String leftWord;

    public Integer getOpportunitiesId() {
        return opportunitiesId;
    }

    public void setOpportunitiesId(Integer opportunitiesId) {
        this.opportunitiesId = opportunitiesId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getLeftWord() {
        return leftWord;
    }

    public void setLeftWord(String leftWord) {
        this.leftWord = leftWord == null ? null : leftWord.trim();
    }
}