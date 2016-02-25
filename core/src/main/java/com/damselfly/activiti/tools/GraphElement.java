package com.damselfly.activiti.tools;

import java.io.Serializable;

/**
 * Created by v on 2014/7/15.
 */
public class GraphElement  implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
