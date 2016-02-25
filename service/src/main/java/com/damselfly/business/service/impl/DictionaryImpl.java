package com.damselfly.business.service.impl;

import com.damselfly.business.entity.Dictionary;
import com.damselfly.business.entity.DictionaryCriteria;
import com.damselfly.business.mapper.DictionaryMapper;
import com.damselfly.business.service.DictionaryService;
import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ZHEJIANG RUIZHENG  on 2014/7/28.
 */
@Service("dictionaryService")
@Transactional
@SuppressWarnings("unchecked")
public class DictionaryImpl extends BaseService implements DictionaryService  {


    private static final Logger logger = LoggerFactory.getLogger(DictionaryImpl.class);

    @Override
    public Page findByPage(Page page, Dictionary dictionary) {
        page.setCount(findAll(page,dictionary).size());
        return page.setRows(baseDao.selectByPage("com.damselfly.business.mapper.DictionaryMapper."+ BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,dictionary),page));
    }

    @Override
    public List<Dictionary> findAll(Page page, Dictionary dictionary) {
        return baseDao.selectList("com.damselfly.business.mapper.DictionaryMapper."+BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,dictionary));
    }

    public DictionaryCriteria getCriteria(Page page,Dictionary dictionary)
    {
        DictionaryCriteria criteria = new DictionaryCriteria();
        DictionaryCriteria.Criteria cri = criteria.createCriteria();
        if (dictionary != null) {
            if (StringUtils.isNotBlank(dictionary.getState())) {
                cri.andStateEqualTo(dictionary.getState());
            }
            else
            {
                cri.andStateEqualTo("A");
            }

            if (StringUtils.isNotBlank(dictionary.getDicName())) {
                cri.andDicNameLike("%"+dictionary.getDicName()+"%");
            }
            if (StringUtils.isNotBlank(dictionary.getDicKey())) {
                cri.andDicKeyEqualTo(dictionary.getDicKey());
            }
            if (StringUtils.isNotBlank(dictionary.getDicCode())) {
                cri.andDicCodeLike("%"+dictionary.getDicCode()+"%");
            }
            if (dictionary.getDicParentId()!=null) {
                cri.andDicParentIdEqualTo(dictionary.getDicParentId());
            }
            if (dictionary.getDicId()!=null) {
                cri.andDicIdEqualTo(dictionary.getDicId());
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    public Dictionary findByKeyAndName(String dicKey,String dicName){
        return baseDao.getMapper(DictionaryMapper.class).selectByKeyAndName(dicKey,dicName);
    }

    public Dictionary findSubDicValue(String dicParentId,String dicName,String dicKey){
        Dictionary dic = new Dictionary();
        Dictionary resultDic = new Dictionary();
        dic.setDicParentId(Integer.parseInt(dicParentId));
        dic.setDicName(dicName);
        dic.setDicKey(dicKey);
        List<Dictionary> dicList = findAll(null,dic);
        if (dicList != null && dicList.size()>0){
            resultDic = dicList.get(0);
        }
        return resultDic;
    }

    /**
     * 查找父级条目
     * @author maoni 2014.10.25
     * @param page
     * @param dictionary
     * @return
     */
    @Override
    public Page findParentDicList(Page page,Dictionary dictionary){
        page.setCount(findParentAll(page,dictionary).size());
        return page.setRows(baseDao.selectByPage("com.damselfly.business.mapper.DictionaryMapper.findParent", getCriteria(page,dictionary),page));
    }

    /**
     * 查找子级条目
     * @author maoni 2014.10.25
     * @param page
     * @param dictionary
     * @return
     */
    @Override
    public Page findChildrenDicList(Page page,Dictionary dictionary){
        page.setCount(findChildrenAll(page,dictionary).size());
        return page.setRows(baseDao.selectByPage("com.damselfly.business.mapper.DictionaryMapper.findChildren", getCriteria(page,dictionary),page));
    }

    /**
     * @param page
     * @param dictionary
     * @return
     */
    public List<Dictionary> findParentAll(Page page, Dictionary dictionary) {
        return baseDao.selectList("com.damselfly.business.mapper.DictionaryMapper.findParent", getCriteria(page,dictionary));
    }

    /**
     * @param page
     * @param dictionary
     * @return
     */
    public List<Dictionary> findChildrenAll(Page page, Dictionary dictionary) {
        return baseDao.selectList("com.damselfly.business.mapper.DictionaryMapper.findChildren", getCriteria(page,dictionary));
    }

    /**
     * 导出时执行全部查询
     * @param dictionary
     * @author maoni
     * @return
     */
    @Override
    public List<Dictionary> findAllDicListForExport(Dictionary dictionary) {
        return baseDao.getMapper(DictionaryMapper.class).findAllDicListForExport(dictionary);
    }

    @Override
    public Dictionary get(int id) {
        Dictionary dictionary =  baseDao.getMapper(DictionaryMapper.class).selectByPrimaryKey(id);
        return dictionary;
    }

    @Override
    public int findMaxDicIdByName(String dicName) {
        return baseDao.getMapper(DictionaryMapper.class).findMaxDicIdByName(dicName);
    }

    @Override
    public String findDicCodeByDicName(String dicName){
        return baseDao.getMapper(DictionaryMapper.class).findDicCodeByDicName(dicName);
    }
}
