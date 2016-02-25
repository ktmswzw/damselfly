package com.damselfly.log;

/** 
 * @description: 值越大，等级越高。
 * @version 1.0
 * @author V
 * @createDate 2014-1-12;下午11:25:07
 */
public enum LogLevel {
	TRACE("TRACE"),
	
	DEBUG("DEBUG"),
	
	INFO("INFO"),
	
	WARN("WARN"),
	
	ERROR("ERROR");
	
	private String value;
	
	LogLevel(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
