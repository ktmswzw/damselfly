package com.damselfly.common.mybatis;

import java.io.Serializable;

/**
 * Created by vincent on 2014/10/13.
 */
public abstract class CustomerContextHolder  implements Serializable {

    public final static String DATA_SOURCE_DEFAULT = "DATA_SOURCE_DEFAULT";
    public final static String DATA_SOURCE_GENERATOR = "DATA_SOURCE_GENERATOR";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    public static String getCustomerType() {
        return contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}
