package com.damselfly.service.core.impl;

import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.RolePermission;
import com.damselfly.entity.RolePermissionCriteria;
import com.damselfly.mapper.RolePermissionMapper;
import com.damselfly.service.core.PermissionService;
import com.damselfly.service.core.RolePermissionDataControlService;
import com.damselfly.service.core.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("rolePermissionService")
@Transactional
@SuppressWarnings("unchecked")
public class RolePermissionServiceImpl extends BaseService implements RolePermissionService {

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RolePermissionDataControlService rolePermissionDataControlService;
	
	@Override
	public void delete(Long id) {
		baseDao.getMapper(RolePermissionMapper.class).deleteByPrimaryKey(id);
	}

    @Override
    public void delete(RolePermission rolePermission) {
        RolePermissionCriteria criteria = new RolePermissionCriteria();
        RolePermissionCriteria.Criteria cri = criteria.createCriteria();

        if(null != rolePermission.getRoleId()){
            cri.andRoleIdEqualTo(rolePermission.getRoleId());
        }
        baseDao.getMapper(RolePermissionMapper.class).deleteByExample(criteria);
    }

    @Override
	public void delete(Iterable<RolePermission> entities) {
	}

	@Override
	public List<RolePermission> findByRoleId(Long roleId) {
		RolePermissionCriteria criteria = new RolePermissionCriteria();
		RolePermissionCriteria.Criteria cri = criteria.createCriteria();
		if(roleId != null){
			cri.andRoleIdEqualTo(roleId);
		}
		List<RolePermission> list = new ArrayList<RolePermission>();
		list = baseDao.getMapper(RolePermissionMapper.class).selectByExample(criteria);
		for(RolePermission rp:list){
			if(rp.getPermissionId() != null){
				rp.setPermission(permissionService.get(rp.getPermissionId()));
				rp.setRolePermissionDataControls(rolePermissionDataControlService.findByRolePermissionId(rp.getPermissionId()));
			}
		}
		return list;
	}

    @Override
    public List<RolePermission> findByRoleId(Page page,Long roleId) {
        RolePermissionCriteria criteria = new RolePermissionCriteria();
        RolePermissionCriteria.Criteria cri = criteria.createCriteria();
        if(roleId != null){
            cri.andRoleIdEqualTo(roleId);
        }

        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }

        List<RolePermission> list = new ArrayList<RolePermission>();

        if(page == null){
            list = baseDao.getMapper(RolePermissionMapper.class).selectByExample(criteria);
        }
        else {
            list = baseDao.selectByPage("com.damselfly.mapper.RolePermissionMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        }
        for(RolePermission rp:list){
            if(rp.getPermissionId() != null){
                rp.setPermission(permissionService.get(rp.getPermissionId()));
                rp.setRolePermissionDataControls(rolePermissionDataControlService.findByRolePermissionId(rp.getPermissionId()));
            }
        }
        return list;
    }

	@Override
	public RolePermission get(Long id) {
		return baseDao.getMapper(RolePermissionMapper.class).selectByPrimaryKey(id);
	}

    @Override
	public void save(RolePermission rolePermission) {
		baseDao.getMapper(RolePermissionMapper.class).insertSelective(rolePermission);

	}

	@Override
	public void save(Iterable<RolePermission> entities) {

	}

	@Override
	public void update(RolePermission rolePermission) {
		baseDao.getMapper(RolePermissionMapper.class).updateByPrimaryKeySelective(rolePermission);

	}

}
