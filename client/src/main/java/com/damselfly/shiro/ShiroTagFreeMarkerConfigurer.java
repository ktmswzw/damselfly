package com.damselfly.shiro;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.damselfly.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateException;

/** 
 * @description: 支持Shiro 标签
 * @version 1.0
 * @author V
 * @createDate 2014-1-19;上午11:15:33
 */
public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {

	 public void afterPropertiesSet() throws IOException, TemplateException {
	     super.afterPropertiesSet();
	     this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
     }

}
