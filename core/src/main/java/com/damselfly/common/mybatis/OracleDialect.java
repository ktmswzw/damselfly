package com.damselfly.common.mybatis;

public class OracleDialect implements Dialect{
    
    protected static final String SQL_END_DELIMITER = ";";
    
    public static void main(String[] args) {
        
        OracleDialect od = new OracleDialect();
        
        String str = "select * from person where name='kjun'";
        
        // 从第6条开始，显示10
        String sql = od.getLimitSqlString(str, 6, 2, 20);
        
        System.out.println(sql);
        
        String count_sql = od.getCountSqlString(sql);
        
        System.out.println(count_sql);
    }
    
    public String getLimitSqlString(String sql, int offset, int limit, int count) {
        
        sql = sql.trim();
        boolean isForUpdate = false;
        if(sql.toLowerCase().endsWith(" for update")){
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        boolean hasOffset = offset > 0;
        
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        if(hasOffset){
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        }else{
            pagingSelect.append("select * from ( ");
        }
        pagingSelect.append(sql);
        if(hasOffset){
            pagingSelect.append(" ) row_ ) where rownum_ <= " + (offset + limit) + " and rownum_ > " + (offset) + "");
        }else{
            pagingSelect.append(" ) where rownum <= " + limit);
        }
        
        if(isForUpdate){
            pagingSelect.append(" for update");
        }
        
        return pagingSelect.toString();
    }
    
    public String getCountSqlString(String sql) {
        sql = trim(sql);
        StringBuffer sb = new StringBuffer(sql.length() + 10);
        sb.append("SELECT COUNT(1)  " + RS_COLUMN + " FROM  ( ");
        sb.append(sql);
        sb.append(" )");
        return sb.toString();
    }
    
    public boolean supportsLimit() {
        return true;
    }
    
    private String trim(String sql) {
        sql = sql.trim();
        if(sql.endsWith(SQL_END_DELIMITER)){
            sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
        }
        return sql;
    }
}
