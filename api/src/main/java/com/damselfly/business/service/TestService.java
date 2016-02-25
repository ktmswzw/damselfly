package com.damselfly.business.service;

import com.damselfly.business.entity.Test;
import com.damselfly.common.mybatis.Page;
import com.damselfly.exception.ServiceException;

import java.util.List;

/**
 * Created by V on Sun Sep 20 22:32:26 GMT+08:00 2015.
 */

public interface TestService {

    Page findByPage(Page page, Test test);

    List<Test> findAll(Page page, Test test);

    int countByExample(Page page,Test test);

    void save(Test test);

    Test get(int id);

    void update(Test test);

    void delete(int id);

}

