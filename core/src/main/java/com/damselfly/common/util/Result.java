package com.damselfly.common.util;

/**
 * Created by v on 2014/5/8.
 */
public class Result {
    private boolean successful = true;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(boolean successful) {
        this(successful, null);
    }

    public Result(String msg) {
        this(false, msg);
    }

    public Result(boolean successful, String msg) {
        this.successful = successful;
        this.msg = msg;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}