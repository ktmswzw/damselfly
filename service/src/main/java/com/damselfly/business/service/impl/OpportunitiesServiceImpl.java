package com.damselfly.business.service.impl;

import com.damselfly.business.entity.Opportunities;
import com.damselfly.business.entity.OpportunitiesCriteria;
import com.damselfly.business.mapper.OpportunitiesMapper;
import com.damselfly.business.service.OpportunitiesService;
import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by V on Tue Nov 25 22:55:43 CST 2014.
 */
@Service("OpportunitiesService")
@Transactional
@SuppressWarnings("unchecked")
public class OpportunitiesServiceImpl  extends BaseService implements OpportunitiesService {


    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void saveMongoDB(Opportunities opportunities) {
        mongoTemplate.save(opportunities);
    }

    @Override
    public void saveMongoDB(String json) {
        mongoTemplate.save(json,"mmmmmmeee");
    }


    @Override
    public Page findByPage(Page page, Opportunities opportunities) {
        page.setCount(countByExample(null,opportunities));
        List<Opportunities> list= findAll(page,opportunities);
        if(list!=null)
            return page.setRows(list);
        else
            return null;
    }
    
    @Override
    public List<Opportunities> findAll(Page page, Opportunities opportunities) {
        return baseDao.selectByPage("com.damselfly.business.mapper.OpportunitiesMapper."+BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,opportunities),page);
    }

    @Override
    public int countByExample(Page page, Opportunities opportunities) {
        return baseDao.getMapper(OpportunitiesMapper.class).countByExample(getCriteria(page,opportunities));
    }

    public OpportunitiesCriteria getCriteria(Page page,Opportunities opportunities)
    {
        OpportunitiesCriteria criteria = new OpportunitiesCriteria();
        OpportunitiesCriteria.Criteria cri = criteria.createCriteria();
        if (opportunities != null) {

            if(StringUtils.isNotBlank(opportunities.getEmail())){
                cri.andEmailLike(opportunities.getEmail());
            }
            if(StringUtils.isNotBlank(opportunities.getLeftWord())){
                cri.andEmailLike(opportunities.getLeftWord());
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    @Override
    public void save(Opportunities opportunities) {
        baseDao.getMapper(OpportunitiesMapper.class).insertSelective(opportunities);
    }

    @Override
    public Opportunities get(int id) {
        return baseDao.getMapper(OpportunitiesMapper.class).selectByPrimaryKey(id);
    }

    @Override
    public void update(Opportunities opportunities) {
        baseDao.getMapper(OpportunitiesMapper.class).updateByPrimaryKeySelective(opportunities);
    }

    @Override
    public void delete(int id) {
        baseDao.getMapper(OpportunitiesMapper.class).deleteByPrimaryKey(id);
    }
}

