package com.damselfly.business.service;

import com.damselfly.business.entity.Opportunities;
import com.damselfly.common.mybatis.Page;

import java.util.List;

/**
 * Created by V on Tue Nov 25 22:55:43 CST 2014.
 */

public interface OpportunitiesService {

    void saveMongoDB(Opportunities opportunities);

    void saveMongoDB(String json);

    Page findByPage(Page page, Opportunities opportunities);

    List<Opportunities> findAll(Page page, Opportunities opportunities);

    int countByExample(Page page,Opportunities opportunities);

    void save(Opportunities opportunities);

    Opportunities get(int id);

    void update(Opportunities opportunities);

    void delete(int id);

}

