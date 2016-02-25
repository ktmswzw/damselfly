package com.damselfly.service.core.impl;

import com.damselfly.common.baseservice.BaseService;
import com.damselfly.entity.UserRole;
import com.damselfly.entity.UserRoleCriteria;
import com.damselfly.mapper.UserRoleMapper;
import com.damselfly.service.core.RoleService;
import com.damselfly.service.core.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userRoleService")
@Transactional
@SuppressWarnings("unchecked")
public class UserRoleServiceImpl extends BaseService implements UserRoleService {

	@Autowired
	private RoleService roleService;
	
	@Override
	public void delete(Long userRoleId) {
		baseDao.getMapper(UserRoleMapper.class).deleteByPrimaryKey(userRoleId);

	}

	@Override
	public List<UserRole> find(Long userId) {
		UserRoleCriteria criteria = new UserRoleCriteria();
		UserRoleCriteria.Criteria cri = criteria.createCriteria();
		if(userId != null){
			cri.andUserIdEqualTo(userId);
		}
		
		List<UserRole> list = baseDao.getMapper(UserRoleMapper.class).selectByExample(criteria);
		for(UserRole ur : list){
			if(ur.getRoleId() != null){
				ur.setRole(roleService.get(ur.getRoleId()));
			}
		}
		return list;
	}

    /**
     *
     * @param userRole
     * @return
     */
    public List<UserRole> find(UserRole userRole) {
        UserRoleCriteria criteria = new UserRoleCriteria();
        UserRoleCriteria.Criteria cri = criteria.createCriteria();
        if(userRole.getUserId()!=null){
            cri.andUserIdEqualTo(userRole.getUserId());
        }if(userRole.getRoleId()!=null){
            cri.andRoleIdEqualTo(userRole.getRoleId());
        }
        List<UserRole> list = baseDao.getMapper(UserRoleMapper.class).selectByExample(criteria);
        return list;
    }

	@Override
	public UserRole get(Long id) {
		return baseDao.getMapper(UserRoleMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void save(UserRole userRole) {
		baseDao.getMapper(UserRoleMapper.class).insertSelective(userRole);
	}

}
