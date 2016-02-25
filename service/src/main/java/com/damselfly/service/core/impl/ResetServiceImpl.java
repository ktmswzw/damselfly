package com.damselfly.service.core.impl;

import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.Reset;
import com.damselfly.entity.ResetCriteria;
import com.damselfly.mapper.ResetMapper;
import com.damselfly.service.core.ResetService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by v on 2014/8/30.
 */
@Service("resetService")
@Transactional
@SuppressWarnings("unchecked")
public class ResetServiceImpl extends BaseService implements ResetService{

    @Override
    public void update(Reset reset) {
        baseDao.getMapper(ResetMapper.class).updateByPrimaryKeySelective(reset);
    }

    @Override
    public void save(Reset reset) {
        //添加前失效之前的数据
        Reset reset1 = new Reset();
        reset1.setMail(reset.getMail());
        List<Reset> list= find(null,reset1);
        for(Reset reset2 : list)
        {
            reset2.setState(0);
            update(reset2);
        }
        baseDao.getMapper(ResetMapper.class).insertSelective(reset);
    }

    @Override
    public List<Reset> find(Page page,Reset reset) {
        return baseDao.selectList("com.damselfly.mapper.ResetMapper." + BaseDao.SELECT_BY_EXAMPLE, getCriteria(page, reset));
    }

    public ResetCriteria getCriteria(Page page,Reset reset)
    {
        ResetCriteria criteria = new ResetCriteria();
        ResetCriteria.Criteria cri = criteria.createCriteria();
        if (reset != null) {
            cri.andStateEqualTo(1);
            if (StringUtils.isNotBlank(reset.getMail())) {
                cri.andMailEqualTo(reset.getMail());
            }
            if (StringUtils.isNotBlank(reset.getSalt())) {
                cri.andSaltEqualTo(reset.getSalt());
            }
            if(reset.getRequestDate()!=null)
            {
                cri.addCriterion(" request_date > date_add(unix_timestamp('"+reset.getRequestDate().toString()+"'), interval -30 minute) ");
            }
            //cri.addCriterion(" cust_Id in (1,2,3)");
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }
}
