package com.damselfly.common.baseservice;

import com.damselfly.common.basedao.BatchHander;
import com.damselfly.common.mybatis.Page;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2012-2-15-上午10:28:58
 */
public interface SuperDaoService extends Service{
    
    public <T> T getMapper(Class<T> type);
    
    @SuppressWarnings("rawtypes")
    public <T> int updateBatch(Class clazz, String upType, List<T> list);
    
    public int updateBatasdch(Object obj, BatchHander batchHander);
    
    public <T> int updateBatch(String statement, List<T> list);
    
    public Object selectOne(String statement, Object parameter);
    
    @SuppressWarnings("rawtypes")
    public List selectList(String statement, Object parameter);
    
    @SuppressWarnings("rawtypes")
    public List selectByPage(String statement, Object parameter, Page page);
    
    public int update(String statement, Object parameter);
    
    public int delete(String statement, Object parameter);
    
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String key);
    
    
    public void execute(String sql) throws Exception;
    
    public Object executeFunction(String funcName, Object params)  ;
}
