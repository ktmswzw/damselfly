package com.damselfly.exception;

import org.springframework.dao.DataAccessException;

/**
 * <b>function:</b>自定义Biz层异常信息
 * @author hoojo
 * @createDate 2011-4-13 上午11:42:19
 * @file BizException.java
 * @package com.hoo.exception
 * @project MyBatisForSpring
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class BizException extends DataAccessException {

	/**
	 * @author Hoojo
	 */
	private static final long serialVersionUID = 1L;
	
	public BizException(String msg) {
		super(msg);
	}

	public BizException(String msg, Throwable cause) {
		super(msg, cause);
	}
}