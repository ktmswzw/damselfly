package com.damselfly.shiro;

import org.apache.shiro.authc.AuthenticationException;

/** 
 * @description: 
 * @version 1.0
 * @author V
 * @createDate 2014-1-13;下午11:10:41
 */
public class IncorrectCaptchaException extends AuthenticationException {
	/** 描述  */
	private static final long serialVersionUID = 6146451562810994591L;

	public IncorrectCaptchaException() {
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}

}
