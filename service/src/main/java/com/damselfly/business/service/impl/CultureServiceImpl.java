package com.damselfly.business.service.impl;

import com.damselfly.business.entity.Culture;
import com.damselfly.business.entity.CultureCriteria;
import com.damselfly.business.mapper.CultureMapper;
import com.damselfly.business.service.CultureService;
import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by V on Wed Dec 31 23:31:05 CST 2014.
 */
@Service("CultureService")
@Transactional
@SuppressWarnings("unchecked")
public class CultureServiceImpl extends BaseService implements CultureService {


    @Override
    public Page findByPage(Page page, Culture culture) {
        page.setCount(countByExample(page,culture));
        List<Culture> list= findAll(page,culture);
        if(list!=null)
            return page.setRows(list);
        else
            return null;
    }
    
    @Override
    public List<Culture> findAll(Page page, Culture culture) {
        return baseDao.selectList("com.damselfly.business.mapper.CultureMapper."+ BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,culture));
    }

    @Override
    public int countByExample(Page page, Culture culture) {
        return baseDao.getMapper(CultureMapper.class).countByExample(getCriteria(page,culture));
    }

    public CultureCriteria getCriteria(Page page,Culture culture)
    {
        CultureCriteria criteria = new CultureCriteria();
        CultureCriteria.Criteria cri = criteria.createCriteria();
        if (culture != null) {
                
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    @Override
    public void save(Culture culture) {
        baseDao.getMapper(CultureMapper.class).insertSelective(culture);
    }

    @Override
    public Culture get(int id) {
        return baseDao.getMapper(CultureMapper.class).selectByPrimaryKey(id);
    }

    @Override
    public void update(Culture culture) {
        baseDao.getMapper(CultureMapper.class).updateByPrimaryKeySelective(culture);
    }

    @Override
    public void delete(int id) {
        baseDao.getMapper(CultureMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Culture culture) {
        baseDao.getMapper(CultureMapper.class).deleteByExample(getCriteria(null,culture));
    }
}

