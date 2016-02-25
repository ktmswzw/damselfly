package com.damselfly.common.basedao;

import com.alibaba.fastjson.JSONObject;
import com.damselfly.common.mybatis.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Transactional
@SuppressWarnings("unchecked")
public class BaseDaoSingleSession extends BaseSqlSessionDaoSupport implements
		BaseDao {

	private Integer batchSize = 20;

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public <T> T getMapper(Class<T> type) {
		return getSqlSession().getMapper(type);
	}

	@SuppressWarnings("rawtypes")
	public <T> int updateBatch(Class clazz, String upType, List<T> list) {
		if (StringUtils.isBlank(upType)) {
			upType = INSERT;
		}
		return updateBatch(clazz.getName() + "." + upType, list);
	}

	@SuppressWarnings("rawtypes")
	public <T> int updateBatch(String statement, List<T> list) {
		int counts = 0;

		SqlSession session = getSqlSession();
		Transaction transaction = null;
		Environment env = null;
		BatchExecutor be = null;
		try {
			Configuration conf = session.getConfiguration();
			conf.setDefaultExecutorType(ExecutorType.BATCH);
			env = conf.getEnvironment();
			JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
			transaction = jdbcTransactionFactory.newTransaction(
					env.getDataSource(),
					TransactionIsolationLevel.REPEATABLE_READ, false);

			be = new BatchExecutor(conf, transaction);
			MappedStatement ms = conf.getMappedStatement(statement);

			if (CollectionUtils.isEmpty(list)) {
				return 0;
			}
			int x = 1;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				be.doUpdate(ms, iter.next());
				if (x % batchSize == 0) {
					be.commit(true);
					transaction.commit();
				}
				// if(x==66)
				// throw new SQLException("aa");
				x++;
				// break;
			}

			be.commit(true);
			transaction.commit();

			List<BatchResult> bl = be.flushStatements();
			if (CollectionUtils.isNotEmpty(bl)) {
				for (BatchResult br : bl) {
					for (int c : br.getUpdateCounts()) {
						counts += c;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				be.rollback(true);
				transaction.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			be.close(false);
		}
		return counts;
	}

	public int updateBatch(Object obj, BatchHander batchHander) {
		int counts = 0;

		SqlSession session = getSqlSession();
		Transaction transaction = null;
		Environment env = null;
		BatchExecutor be = null;
		try {
			Configuration conf = session.getConfiguration();
			conf.setDefaultExecutorType(ExecutorType.BATCH);
			env = conf.getEnvironment();
			JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
			transaction = jdbcTransactionFactory.newTransaction(
					env.getDataSource(),
					TransactionIsolationLevel.REPEATABLE_READ, false);
			be = new BatchExecutor(conf, transaction);
			batchHander.batch(conf, be, transaction, obj);
			counts = batchHander.getUpdateCounts(be, counts);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				be.rollback(true);
				transaction.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			be.close(false);
		}
		return counts;
	}

	public int insert(String statement, Object parameter) {
		return getSqlSession().insert(statement, parameter);
	}

	public int update(String statement, Object parameter) {
		return getSqlSession().update(statement, parameter);
	}

	public int delete(String statement, Object parameter) {
		return getSqlSession().delete(statement, parameter);
	}

	public Object selectOne(String statement, Object parameter) {
		return getSqlSession().selectOne(statement, parameter);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String statement, Object parameter) {
		return getSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("rawtypes")
	public List selectByPage(String statement, Object parameter, Page page) {
		return getSqlSession().selectList(statement, parameter, page);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter,
			String key) {
		return getSqlSession().selectMap(statement, parameter, key);
	}


	public void execute(String sql) throws Exception {
		Connection conn = null;
		try {
			conn = getSqlSession().getConnection();
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setStopOnError(true);
			runner.runScript(new StringReader(sql));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object executeFunction(String funcName, Object... param)
			throws Exception {
		JSONObject func = new JSONObject();
		func.put("funcName", funcName);
		List<Object> paramList = null;
		if (param != null)
			paramList = Arrays.asList(param);
		func.put("params", paramList);
		return this.selectOne("mybatis.sqlmapper.SystemMapper.wmsfunc", func);

	}



	@Override
	public SqlSessionFactory getSqlSessionFactory() {
		// Auto-generated method stub
		return super.sqlSessionFactory;
	}

}
