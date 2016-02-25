package com.damselfly.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by v on 2014/7/7.
 */
public class RepeatLoginException extends AuthenticationException {

    private static final long serialVersionUID = 3184431913337299518L;

    public RepeatLoginException() {
        super();
    }

    public RepeatLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatLoginException(String message) {
        super(message);
    }

    public RepeatLoginException(Throwable cause) {
        super(cause);
    }
}
