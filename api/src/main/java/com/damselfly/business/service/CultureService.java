package com.damselfly.business.service;

import com.damselfly.business.entity.Culture;
import com.damselfly.common.mybatis.Page;

import java.util.List;

/**
 * Created by V on Wed Dec 31 23:31:05 CST 2014.
 */

public interface CultureService {

    Page findByPage(Page page, Culture culture);

    List<Culture> findAll(Page page, Culture culture);

    int countByExample(Page page, Culture culture);

    void save(Culture culture);

    Culture get(int id);

    void update(Culture culture);

    void delete(int id);

    void delete(Culture culture);

}

