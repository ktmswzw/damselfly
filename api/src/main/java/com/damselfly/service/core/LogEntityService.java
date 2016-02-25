package com.damselfly.service.core;

import java.util.List;

import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.LogEntity;
import com.damselfly.log.LogLevel;


/** 
 * @description: 登录日志
 * @version 1.0
 * @author V
 * @createDate 2014-1-11;下午02:16:30
 */
public interface LogEntityService {

void save(LogEntity logEntity);
	
	LogEntity get(Long id);
	
	void update(LogEntity logEntity);
	
	void delete(Long id);
	
	List<LogEntity> findByLogLevel(LogLevel logLevel, Page page);
	
	List<LogEntity> findAll();
	
	List<LogEntity> findByExample(LogEntity logEntity, Page page);
	
}
