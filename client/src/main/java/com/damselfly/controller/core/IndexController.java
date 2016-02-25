package com.damselfly.controller.core;

import com.damselfly.common.SecurityConstants;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.entity.Module;
import com.damselfly.entity.Permission;
import com.damselfly.service.core.ModuleService;
import com.damselfly.shiro.SecurityUtils;
import com.damselfly.shiro.ShiroUser;
import com.google.common.collect.Lists;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/** 
 * @description: 后台主页
 * @version 1.0
 * @author V
 * @createDate 2014-1-19;下午05:49:53
 */
@Controller
@RequestMapping("/console/index")
public class IndexController extends BaseAction{

	private static final String INDEX = "console/index/index";
	private static final String PORTAL = "console/index/portal";
	private static final String LOADING = "console/layout/loading";

	@Autowired
	private ModuleService moduleService;

	@RequestMapping(value="",  method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		// 加入ipAddress
		shiroUser.setIpAddress(request.getRemoteAddr());
		
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		
		return INDEX;
	}
	
	@RequestMapping(value="/getMenuModule", method=RequestMethod.POST) 
	@ResponseBody
	public Module getMenuModule() {
		
		Subject subject = SecurityUtils.getSubject();
		
		Module rootModule = moduleService.getTree();

		if(rootModule != null){
			check(rootModule, subject);
		}else{
			rootModule = new Module();
		}
		
		return rootModule;
	}

	
	private void check(Module module, Subject subject) {
		List<Module> list1 = Lists.newArrayList();
		for (Module m1 : module.getChildren()) {
			// 只加入拥有view权限的Module
			if (subject.isPermitted(m1.getSn() + ":"
					+ Permission.PERMISSION_SHOW)) {
				check(m1, subject);
				list1.add(m1);
			}
		}
		module.setChildren(list1);
	}
	
	@RequestMapping(value="portal", method=RequestMethod.GET)
	public String portal() {
		
		return PORTAL;
	}


	@RequestMapping(value="loading")
	public String loading() {
		return LOADING;
	}
}
