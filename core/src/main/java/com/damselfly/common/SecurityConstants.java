package com.damselfly.common;

/** 
 * @description: 常量
 * @version 1.0
 * @author V
 * @createDate 2014-1-10;下午10:31:09
 */
public interface SecurityConstants {
	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "login_user";    
	
	/**
	 * 验证码
	 */
	public final static String CAPTCHA_KEY = "captcha_key";
	
	/**
	 * 日志参数
	 */
	public final static String LOG_ARGUMENTS = "log_arguments";
	
	/**
	 * 动态查询，参数前缀
	 */
	public final static String SEARCH_PREFIX = "search_";

    /**
     * 动态表单，参数前缀
     */
    public final static String DYNAMIC_PREFIX = "fp_";
	
	/**
	 * 内部动态查询参数常量
	 */
	public final static String NEST_DYNAMIC_SEARCH = "$nest_dynamic_search$";
	
	/**
	 * 内部动态查询参数常量，logical
	 */
	public final static String NEST_DYNAMIC_SEARCH_LOGICAL = "$nest_dynamic_search_logical$";
	
	public static final String USERNAME_IS_NULL="用户名为空!";
	public static final String LOGIN_IS_EXIST="该用户已登录!";
	public static final String LOGIN_IS_SUCCESS="登录成功!";
	public static final String UNKNOWN_SESSION_EXCEPTION="异常会话!";
	public static final String UNKNOWN_ACCOUNT_EXCEPTION="账号错误!";
	public static final String INCORRECT_CREDENTIALS_EXCEPTION="密码错误!";
	public static final String LOCKED_ACCOUNT_EXCEPTION="账号已被锁定，请与系统管理员联系!";
	public static final String DISABLED_ACCOUNT_EXCEPTION="账号被冻结，请与系统管理员联系!";
	public static final String INCORRECT_CAPTCHA_EXCEPTION= "验证码错误!";
	public static final String REPEAT_LOGIN_EXCEPTION= "重复登陆!!";
	public static final String AUTHENTICATION_EXCEPTION= "您没有授权!";
	public static final String UNKNOWN_EXCEPTION= "出现未知异常,请与系统管理员联系!";
	public static final String TEXT_TYPE_PLAIN= "text/plain";
	public static final String TEXT_TYPE_HTML= "text/html";
	public static final String SESSION_FORCE_LOGOUT_KEY= "session.force.logout";
}
