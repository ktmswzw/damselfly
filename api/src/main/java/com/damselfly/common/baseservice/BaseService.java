package com.damselfly.common.baseservice;

import com.damselfly.common.basedao.BaseDao;

import javax.annotation.Resource;

public class BaseService implements Service {
    protected BaseDao baseDao;
//    CacheManager cacheManager = CacheManager.create();

    @Resource(name = "baseDao")
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

/*    public void updateEhcache(String ehcacheName, String key) {
        Cache sample = cacheManager.getCache(ehcacheName);
        sample.remove(key);
    }*/
}

