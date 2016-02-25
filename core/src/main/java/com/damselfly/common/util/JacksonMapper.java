package com.damselfly.common.util;

/**
 * Created by v on 2014/7/18.
 */

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xuanyin
 *
 */
public class JacksonMapper {

    /**
     *
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     */
    private JacksonMapper() {

    }

    /**
     *
     * @return
     */
    public static ObjectMapper getInstance() {

        return mapper;
    }

}