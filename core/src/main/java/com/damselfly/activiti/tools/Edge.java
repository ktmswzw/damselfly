package com.damselfly.activiti.tools;

import java.io.Serializable;

/**
 * Created by v on 2014/7/15.
 */
public class Edge extends GraphElement  implements Serializable {
    private Node src;
    private Node dest;

    public Node getSrc() {
        return src;
    }

    public void setSrc(Node src) {
        this.src = src;
    }

    public Node getDest() {
        return dest;
    }

    public void setDest(Node dest) {
        this.dest = dest;
    }
}
