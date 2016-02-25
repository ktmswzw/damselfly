package com.damselfly.common.baseservice;



import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.basedao.BatchHander;
import com.damselfly.common.mybatis.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("superDaoService")

public class SuperDaoServiceImpl implements SuperDaoService{
    
    private BaseDao baseDao;
    
    @Resource
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
    public <T> T getMapper(Class<T> type) {
        return baseDao.getMapper(type);
    }
    
    @SuppressWarnings("rawtypes")
    public <T> int updateBatch(Class clazz, String upType, List<T> list) {
        return baseDao.updateBatch(clazz, upType, list);
    }
    
    public int updateBatasdch(Object obj, BatchHander batchHander) {
        return baseDao.updateBatch(obj, batchHander);
    }
    
    public <T> int updateBatch(String statement, List<T> list) {
        return baseDao.updateBatch(statement, list);
    }
    
    public Object selectOne(String statement, Object parameter) {
        return baseDao.selectOne(statement, parameter);
    }
    
    @SuppressWarnings("rawtypes")
    public List selectList(String statement, Object parameter) {
        return baseDao.selectList(statement, parameter);
    }
    
    @SuppressWarnings("rawtypes")
    public List selectByPage(String statement, Object parameter, Page page) {
        return baseDao.selectByPage(statement, parameter, page);
    }
    
    public int update(String statement, Object parameter) {
        return baseDao.update(statement, parameter);
    }
    
    public int delete(String statement, Object parameter) {
        return baseDao.delete(statement, parameter);
    }
    
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String key) {
        return baseDao.selectMap(statement, parameter, key);
    }
    public void execute(String sql) throws Exception{
        baseDao.execute(sql);
    }

	@Override
	public Object executeFunction(String funcName, Object params) {
		
	   try {
		return  baseDao.executeFunction(funcName, params);
	     } catch (Exception e) {
	   }
	   
	   return null;
	}
    
}
