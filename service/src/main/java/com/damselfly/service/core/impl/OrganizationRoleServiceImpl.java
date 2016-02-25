package com.damselfly.service.core.impl;

import com.damselfly.common.baseservice.BaseService;
import com.damselfly.entity.OrganizationRole;
import com.damselfly.entity.OrganizationRoleCriteria;
import com.damselfly.mapper.OrganizationRoleMapper;
import com.damselfly.service.core.OrganizationRoleService;
import com.damselfly.service.core.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("organizationRoleService")
@Transactional
@SuppressWarnings("unchecked")
public class OrganizationRoleServiceImpl extends BaseService implements OrganizationRoleService {

	@Resource(name="roleService")
	private RoleService roleService;
	
	@Override
	public void delete(Long organizationRoleId) {
		baseDao.getMapper(OrganizationRoleMapper.class).deleteByPrimaryKey(organizationRoleId);
	}

	@Override
	public List<OrganizationRole> find(Long organizationId) {
		OrganizationRoleCriteria criteria = new OrganizationRoleCriteria();
		OrganizationRoleCriteria.Criteria cri = criteria.createCriteria();
		if(organizationId != null){
			cri.andOrganizationIdEqualTo(organizationId);
		}
		
		List<OrganizationRole> list = baseDao.getMapper(OrganizationRoleMapper.class).selectByExample(criteria);
		for(OrganizationRole or : list){
			if(or.getRoleId() != null){
				or.setRole(roleService.get(or.getRoleId()));
			}
		}
		
		return list;
	}

	@Override
	public OrganizationRole get(Long id) {
		return baseDao.getMapper(OrganizationRoleMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void save(OrganizationRole organizationRole) {
		baseDao.getMapper(OrganizationRoleMapper.class).insertSelective(organizationRole);

	}

}
