package com.damselfly.service.core.impl;

import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.RolePermissionDataControl;
import com.damselfly.entity.RolePermissionDataControlCriteria;
import com.damselfly.mapper.DataControlMapper;
import com.damselfly.mapper.RolePermissionDataControlMapper;
import com.damselfly.service.core.RolePermissionDataControlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
@Service("rolePermissionDataControlService")
public class RolePermissionDataControlServiceImpl extends BaseService implements 
		RolePermissionDataControlService {

	
	@Override
	public void delete(Long id) {
		baseDao.getMapper(RolePermissionDataControlMapper.class).deleteByPrimaryKey(id);

	}

	@Override
	public void delete(Iterable<RolePermissionDataControl> entities) {

	}

	@Override
	public List<RolePermissionDataControl> findAll(Page page) {
		RolePermissionDataControlCriteria criteria = new RolePermissionDataControlCriteria();
		RolePermissionDataControlCriteria.Criteria cri = criteria.createCriteria();
		if(page == null){
			return baseDao.getMapper(RolePermissionDataControlMapper.class).selectByExample(criteria);
		}
		return baseDao.selectByPage(BaseDao.SELECT_BY_EXAMPLE, criteria, page);
	}

	@Override
	public List<RolePermissionDataControl> findByExample(
			RolePermissionDataControl rolePermissionDataControl, Page page) {
		RolePermissionDataControlCriteria criteria = new RolePermissionDataControlCriteria();
		RolePermissionDataControlCriteria.Criteria cri = criteria.createCriteria();
		
		if(rolePermissionDataControl != null){
			if(rolePermissionDataControl.getDataControlId() != null){
				cri.andDataControlIdEqualTo(rolePermissionDataControl.getDataControlId());
			}
			
			if(rolePermissionDataControl.getRolePermissionId() != null){
				cri.andRolePermissionIdEqualTo(rolePermissionDataControl.getRolePermissionId());
			}
		}
		
		if(page == null){
			return baseDao.getMapper(RolePermissionDataControlMapper.class).selectByExample(criteria);
		}
		return baseDao.selectByPage(BaseDao.SELECT_BY_EXAMPLE, criteria, page);
	}

	@Override
	public List<RolePermissionDataControl> findByRoleId(Long roleId) {
		return null;
	}

	@Override
	public List<RolePermissionDataControl> findByRolePermissionId(
			Long rolePermissionId) {
		RolePermissionDataControlCriteria criteria = new RolePermissionDataControlCriteria();
		RolePermissionDataControlCriteria.Criteria cri = criteria.createCriteria();
		if(rolePermissionId != null){
			cri.andRolePermissionIdEqualTo(rolePermissionId);
		}
		
		List<RolePermissionDataControl> list = new ArrayList<RolePermissionDataControl>();
		list = baseDao.getMapper(RolePermissionDataControlMapper.class).selectByExample(criteria);
		for(RolePermissionDataControl rpdc:list){
			if(rpdc.getDataControlId() != null){
				rpdc.setDataControl(baseDao.getMapper(DataControlMapper.class).selectByPrimaryKey(rpdc.getDataControlId()));
			}
		}
		return list;
	}

	@Override
	public RolePermissionDataControl get(Long id) {
		return baseDao.getMapper(RolePermissionDataControlMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void save(Iterable<RolePermissionDataControl> entities) {
	}

	@Override
	public void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl) {
		if(rolePermissionDataControl.getId() != null){
			baseDao.getMapper(RolePermissionDataControlMapper.class).updateByPrimaryKeySelective(rolePermissionDataControl);
		}else{
			baseDao.getMapper(RolePermissionDataControlMapper.class).insertSelective(rolePermissionDataControl);
		}

	}

}
