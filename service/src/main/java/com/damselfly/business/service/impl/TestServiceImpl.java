package com.damselfly.business.service.impl;

import com.damselfly.business.entity.Test;
import com.damselfly.business.entity.TestCriteria;
import com.damselfly.business.mapper.TestMapper;
import com.damselfly.business.service.TestService;
import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by V on Sun Sep 20 22:32:26 GMT+08:00 2015.
 */
@Service("TestService")
@Transactional
@SuppressWarnings("unchecked")
public class TestServiceImpl  extends BaseService implements TestService {


    @Override
    public Page findByPage(Page page, Test test) {
        page.setCount(countByExample(page,test));
        List<Test> list= findAll(page,test);
        if(list!=null)
            return page.setRows(list);
        else
            return null;
    }
    
    @Override
    public List<Test> findAll(Page page, Test test) {
        return baseDao.selectByPage("com.damselfly.business.mapper.TestMapper."+BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,test),page);
    }

    @Override
    public int countByExample(Page page, Test test) {
        return baseDao.getMapper(TestMapper.class).countByExample(getCriteria(page,test));
    }

    public TestCriteria getCriteria(Page page,Test test)
    {
        TestCriteria criteria = new TestCriteria();
        TestCriteria.Criteria cri = criteria.createCriteria();
        if (test != null) {
                
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    @Override
    public void save(Test test) {
        baseDao.getMapper(TestMapper.class).insertSelective(test);
    }

    @Override
    public Test get(int id) {
        return baseDao.getMapper(TestMapper.class).selectByPrimaryKey(id);
    }

    @Override
    public void update(Test test) {
        baseDao.getMapper(TestMapper.class).updateByPrimaryKeySelective(test);
    }

    @Override
    public void delete(int id) {
        baseDao.getMapper(TestMapper.class).deleteByPrimaryKey(id);
    }
}

