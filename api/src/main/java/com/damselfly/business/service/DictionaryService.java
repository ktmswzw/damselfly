package com.damselfly.business.service;

import com.damselfly.business.entity.Dictionary;
import com.damselfly.common.mybatis.Page;

import java.util.List;

/**
 * Created by ZHEJIANG RUIZHENG  on 2014/7/28.
 */
public interface DictionaryService {
    Page findByPage(Page page, Dictionary dictionary);

    List<Dictionary> findAll(Page page, Dictionary dictionary);

    Dictionary findByKeyAndName(String name, String key);

    Dictionary findSubDicValue(String dicParentId, String dicName, String dicKey);

    Page findParentDicList(Page page, Dictionary dictionary);

    Page findChildrenDicList(Page page, Dictionary dictionary);

    List<Dictionary> findAllDicListForExport(Dictionary dictionary);


    Dictionary get(int id);

    int findMaxDicIdByName(String dicName);

    String findDicCodeByDicName(String dicName);

}
