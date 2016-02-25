package com.damselfly.common.basedao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @author
 * @date   2012-3-14 - 下午05:40:38
 */
public abstract class BatchHander  implements Serializable {

	//批处理
    public abstract void batch(Configuration conf, BatchExecutor be, Transaction transaction, Object obj) throws SQLException;
    
    public final int getUpdateCounts(BatchExecutor be,int counts) throws SQLException {
        List<BatchResult> bl = be.flushStatements();
        if(CollectionUtils.isNotEmpty(bl)){
            for(BatchResult br:bl){
                for(int c:br.getUpdateCounts()){
                    counts += c;
                }
            }
        }
        return counts;
    }
    
    public final void execute(BatchExecutor be,Configuration conf,String statement,Object obj) throws SQLException{
         be.doUpdate(conf.getMappedStatement(statement),obj);
    }
    
    public final void groupCommit(BatchExecutor be, Transaction transaction) throws SQLException{
        be.commit(true);
        transaction.commit();
    }
}
