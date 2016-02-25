package com.damselfly.common.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
//import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import com.damselfly.common.util.ReflectUtil;




/***
 *
 * <b>Summary: </b>
 *       MYBATIS拦截器
 * <b>Remarks: </b>
 *       MYBATIS拦截器用于分页
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DiclectStatementHandlerInterceptor implements Interceptor {
    private static final Log logger = LogFactory.getLog(DiclectStatementHandlerInterceptor.class);
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
        PreparedStatementHandler handler = (PreparedStatementHandler) ReflectUtil.getFieldValue(statement, "delegate");
        RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(handler, "rowBounds");
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(handler, "mappedStatement");

        //获取数据库方言
        String dialectClass= mappedStatement.getConfiguration().getVariables().getProperty("dialect");
        if(StringUtils.isBlank(dialectClass)){
            dialectClass="com.hrz.core.mybatis.MySQLDialect";//默认方言
        }
        Dialect dialect =  (Dialect) Class.forName(dialectClass).newInstance();



        logger.debug("MappedStatement ID : "+mappedStatement.getId());
        logger.debug("MappedStatement File : "+mappedStatement.getResource());
        if(rowBounds.getLimit() > 0 && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT){
            BoundSql boundSql = statement.getBoundSql();
            String sql = boundSql.getSql();


            String pageSql = dialect.getLimitSqlString(sql, rowBounds.getOffset(), rowBounds.getLimit(), 0);

            BoundSql dgBoundSql = handler.getBoundSql();
            Connection connection = (Connection) invocation.getArgs()[0];
            Object parameterObject = dgBoundSql.getParameterObject();
            String countSql = dialect.getCountSqlString(sql);
            PreparedStatement countStmt = connection.prepareStatement(countSql);
            setParameters(countStmt, mappedStatement, boundSql, parameterObject);
            ResultSet rs = countStmt.executeQuery();
            int count = 0;
            if(rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            countStmt.close();
            Page page = (Page) rowBounds;
            ReflectUtil.setFieldValue(page, "count", count);
            // ------------------------------------------------------------------
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }

        return invocation.proceed();
    }

    /**
     * DefaultParameterHandler
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
            throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if(parameterMappings != null){
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for(int i = 0; i < parameterMappings.size(); i++){
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if(parameterMapping.getMode() != ParameterMode.OUT){
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if(parameterObject == null){
                        value = null;
                    }else if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())){
                        value = parameterObject;
                    }else if(boundSql.hasAdditionalParameter(propertyName)){
                        value = boundSql.getAdditionalParameter(propertyName);
                    }else if(propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())){
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if(value != null){
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    }else{
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if(typeHandler == null){
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement "
                                + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * @param sql
     * @param page
     * @return
     */
    @SuppressWarnings("unused")
    private String generatePageSql(String sql) {
        return null;
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {

    }

}
