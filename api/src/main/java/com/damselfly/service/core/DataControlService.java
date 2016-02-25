package com.damselfly.service.core;

import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.DataControl;

import java.util.List;


public interface DataControlService {

	DataControl get(Long id);
	
	DataControl getByName(String name);

	void saveOrUpdate(DataControl dataControl);

	void delete(Long id);
	
	List<DataControl> findAll(Page page);
	
	List<DataControl> findByExample(DataControl dataControl, Page page);
}
