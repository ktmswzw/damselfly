package com.damselfly.service.core;

import java.util.List;

import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.RolePermissionDataControl;


/** 
 * @description: 
 * @version 1.0
 * @author V
 * @createDate 2014-1-11;下午02:14:56
 */
public interface RolePermissionDataControlService {

	RolePermissionDataControl get(Long id);

	void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl);

	void delete(Long id);
	
	List<RolePermissionDataControl> findAll(Page page);
	
	List<RolePermissionDataControl> findByExample(RolePermissionDataControl rolePermissionDataControl, Page page);
	
	void save(Iterable<RolePermissionDataControl> entities);
	
	void delete(Iterable<RolePermissionDataControl> entities);

	List<RolePermissionDataControl> findByRolePermissionId(Long rolePermissionId);
	
	List<RolePermissionDataControl> findByRoleId(Long roleId);
}
