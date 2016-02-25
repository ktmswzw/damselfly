package com.damselfly.service.core.impl;

import com.damselfly.common.basedao.BaseDao;
import com.damselfly.common.baseservice.BaseService;
import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.Role;
import com.damselfly.entity.RoleCriteria;
import com.damselfly.mapper.RoleMapper;
import com.damselfly.service.core.RolePermissionService;
import com.damselfly.service.core.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
@SuppressWarnings("unchecked")
@Cacheable(value="Role")
public class RoleServiceImpl extends BaseService implements RoleService {
	
	@Autowired
	private RolePermissionService rolePermissionService;



/*
    @Delete("delete from student where id = #{id}")
    public void delete(@Param("id")Integer id);
*/


    @Override
    @CacheEvict(value = "Role", key="#id")
    public void delete(Long id) {
		baseDao.getMapper(RoleMapper.class).deleteByPrimaryKey(id);
//		shiroRealm.clearAllCachedAuthorizationInfo();
	}

    @Override
    @Cacheable(value = "Role")
    public Page findByPage(Page page, Role role) {
        page.setCount(find(null,role).size());
        return page.setRows(find(page,role));
    }

    public  RoleCriteria getCriteria(Page page,Role role)
    {
        RoleCriteria criteria = new RoleCriteria();
        RoleCriteria.Criteria cri = criteria.createCriteria();
        if (role != null) {
            if(StringUtils.isNotBlank(role.getDescription())){
                cri.andDescriptionLike(role.getDescription());
            }
            if(StringUtils.isNotBlank(role.getName())){
                cri.andNameEqualTo(role.getName());
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }



    @Override
    @Cacheable(value = "Role")
	public List<Role> find(Page page, Role role) {
        if(page == null)
            return baseDao.getMapper(RoleMapper.class).selectByExample(getCriteria(page,role));
        else {
            List<Role> list = baseDao.selectByPage("com.damselfly.mapper.RoleMapper." + BaseDao.SELECT_BY_EXAMPLE, getCriteria(page, role), page);
            return  list;
        }
	}

    @Override
    @Cacheable(value = "Role")
	public List<Role> findAll(){
		RoleCriteria criteria = new RoleCriteria();
		return baseDao.getMapper(RoleMapper.class).selectByExample(criteria);
	}

	@Override
    @Cacheable(value = "Role", key = "#id")
	public Role get(Long id) {
		Role role = baseDao.getMapper(RoleMapper.class).selectByPrimaryKey(id);
		if(role != null){
			role.setRolePermissions(rolePermissionService.findByRoleId(role.getId()));
		}
		return role;
	}

	@Override
    @CachePut(value = "Role", key = "#role.id")
	public void save(Role role) {
        baseDao.getMapper(RoleMapper.class).insertSelective(role);
	}

	@Override
    @CachePut(value = "Role", key = "#role.id")
	public void update(Role role) {
        baseDao.getMapper(RoleMapper.class).updateByPrimaryKeySelective(role);
	}

}
