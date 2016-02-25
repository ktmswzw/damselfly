package com.damselfly.service.core;

import com.damselfly.entity.Permission;

import java.util.List;


/** 
 * @description: 授权操作
 * @version 1.0
 * @author V
 * @createDate 2014-1-11;下午02:11:36
 */
public interface PermissionService {

	void save(Permission permission);
	
	Permission get(Long id);

    List<Permission> get(Permission permission);

    List<Permission> getAll();
	
	void update(Permission permission);
	
	void delete(Long id);

	
}
