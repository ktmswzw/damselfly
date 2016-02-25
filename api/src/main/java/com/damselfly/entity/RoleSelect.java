package com.damselfly.entity;

import java.io.Serializable;

/**
 * Created by v on 2014/8/28.
 */
public class RoleSelect implements Serializable {

    private static final long serialVersionUID = -4258085473902765108L;

    private Long id;
    private String description;
    private boolean selected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
