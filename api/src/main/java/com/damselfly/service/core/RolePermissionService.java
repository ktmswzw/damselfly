package com.damselfly.service.core;

import java.util.List;

import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.RolePermission;


/** 
 * @description: 角色授权操作关联
 * @version 1.0
 * @author V
 * @createDate 2014-1-11;下午02:12:59
 */
public interface RolePermissionService {

	void save(RolePermission rolePermission);
	
	RolePermission get(Long id);

	void update(RolePermission rolePermission);
	
	void delete(Long id);

    void delete(RolePermission rolePermission);

	List<RolePermission> findByRoleId(Long roleId);

	List<RolePermission> findByRoleId(Page page,Long roleId);

	void save(Iterable<RolePermission> entities);
	
	void delete(Iterable<RolePermission> entities);
	
}
