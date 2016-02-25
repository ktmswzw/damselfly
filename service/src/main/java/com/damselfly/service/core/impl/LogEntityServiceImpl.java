package com.damselfly.service.core.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.LogEntity;
import com.damselfly.entity.LogEntityCriteria;
import com.damselfly.log.LogLevel;
import com.damselfly.mapper.LogEntityMapper;
import com.damselfly.service.core.LogEntityService;
import org.springframework.transaction.annotation.Transactional;

@Service("logEntityService")
@Transactional
@SuppressWarnings("unchecked")
public class LogEntityServiceImpl extends BaseService implements LogEntityService {
	
	@Override
	public void delete(Long id) {
		baseDao.getMapper(LogEntityMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<LogEntity> findAll() {
		LogEntityCriteria criteria = new LogEntityCriteria();
		LogEntityCriteria.Criteria cri = criteria.createCriteria();
		return baseDao.getMapper(LogEntityMapper.class).selectByExample(criteria);
	}

	@Override
	public List<LogEntity> findByExample(LogEntity logEntity, Page page) {
		LogEntityCriteria criteria = new LogEntityCriteria();
		LogEntityCriteria.Criteria cri = criteria.createCriteria();
		
		if(logEntity != null){
			if(StringUtils.isNotBlank(logEntity.getIpAddress())){
				cri.andIpAddressEqualTo(logEntity.getIpAddress());
			}
			
			if(StringUtils.isNotBlank(logEntity.getUsername())){
				cri.andUsernameEqualTo(logEntity.getUsername());
			}
			
			if(StringUtils.isNotBlank(logEntity.getLogLevel())){
				cri.andLogLevelEqualTo(logEntity.getLogLevel());
			}
			
			if(StringUtils.isNotBlank(logEntity.getMessage())){
				cri.andMessageLike("%" + logEntity.getMessage() + "%");
			}
		}
		
		if(page == null){
			return baseDao.getMapper(LogEntityMapper.class).selectByExample(criteria);
		}
		
		return baseDao.selectByPage(BaseDao.SELECT_BY_EXAMPLE, criteria, page);
	}

	@Override
	public List<LogEntity> findByLogLevel(LogLevel logLevel, Page page) {
		LogEntityCriteria criteria = new LogEntityCriteria();
		LogEntityCriteria.Criteria cri = criteria.createCriteria();
		
		if(logLevel != null){
			cri.andLogLevelEqualTo(logLevel.value());
		}
		
		if(page == null){
			return baseDao.getMapper(LogEntityMapper.class).selectByExample(criteria);
		}
		
		return baseDao.selectByPage(BaseDao.SELECT_BY_EXAMPLE, criteria, page);
	}

	@Override
	public LogEntity get(Long id) {
		return baseDao.getMapper(LogEntityMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void save(LogEntity logEntity) {
		baseDao.getMapper(LogEntityMapper.class).insertSelective(logEntity);

	}

	@Override
	public void update(LogEntity logEntity) {
		baseDao.getMapper(LogEntityMapper.class).updateByPrimaryKeySelective(logEntity);

	}

}
