package com.damselfly.common.mybatis;

public interface Dialect{
    
    public static final String RS_COLUMN = "totalCount";
    
    public boolean supportsLimit();
    
    // public String getLimitString(String sql, boolean hasOffset);
    
    /**
     * 以传入SQL为基组装分页查询的SQL语句，传递给iBtatis调用
     * @param sql 原始SQL
     * @param offset 分页查询的记录的偏移
     * @param limit 每页限定记录
     * @return 拼装好的SQL
     */
    public String getLimitSqlString(String sql, int offset, int limit, int count);
    
    /**
     * 以传入SQL为基组装总记录数查询的SQL语句
     * @param sql 原始SQL
     * @return 拼装好的SQL
     */
    public String getCountSqlString(String sql);
}
