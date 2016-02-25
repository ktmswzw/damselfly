package com.damselfly.common.basedao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;

import com.damselfly.common.mybatis.Page;

/**
 * @author
 * @date 2012-2-14-下午05:32:27
 */
public interface BaseDao{
    
	public static final String SELECT_BY_EXAMPLE 			   = "selectByExample";
	
    public static final String DELETE_BY_PRIMARY_KEY           = "deleteByPrimaryKey";
    
    public static final String DELETE_BY_EXAMPLE               = "deleteByExample";
    
    public static final String INSERT                          = "insert";
    
    public static final String INSERT_SELECTIVE                = "insertSelective";
    
    public static final String UPDATE_BY_PRIMARY_KEY_SELECTIVE = "updateByPrimaryKeySelective";
    
    public static final String UPDATE_BY_PRIMARY_KEY           = "updateByPrimaryKey";
    
    public static final String NULL                            = "NULL";
    
    public static final String TYPE_NAME                       = "TypeName";
    
    public static final String LABEL                           = "Label";
    
    public static final String CLASS_NAME                      = "ClassName";
    
    public static final String NAME                            = "Name";
    
    public static final String TABLE_NAME                      = "TableName";
    
    public static final String DABABASE_NAME                   = "DababaseName";
    
    public static final String SCHEMA_NAME                     = "SchemaName";
    
    public <T> T getMapper(Class<T> type);
    
    public int insert(String statement, Object parameter);
    
    public int delete(String statement, Object parameter);
    
    @SuppressWarnings("rawtypes")
    public <T> int updateBatch(Class clazz, String upType, List<T> list);
    
    public <T> int updateBatch(String statement, List<T> list);
    
    public int updateBatch(Object obj, BatchHander batchHander);
    
    public int update(String statement, Object parameter);
    
    public <T> T selectOne(String statement, Object parameter);
    
    @SuppressWarnings("rawtypes")
    public List selectList(String statement, Object parameter);
    
    @SuppressWarnings("rawtypes")
    public List selectByPage(String statement, Object parameter, Page page);
    
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String key);
    
    
    public void execute(String sql) throws Exception;
    
    
    public Object executeFunction(String funcName,Object ...param) throws Exception;
    
    public SqlSessionFactory getSqlSessionFactory();
    
    

    
}
