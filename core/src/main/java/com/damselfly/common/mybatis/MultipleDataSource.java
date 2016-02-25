package com.damselfly.common.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.io.Serializable;

/**
 * Created by vincent on 2014/10/13.
 */
public class MultipleDataSource extends AbstractRoutingDataSource  implements Serializable {

    @Override
    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }
}
