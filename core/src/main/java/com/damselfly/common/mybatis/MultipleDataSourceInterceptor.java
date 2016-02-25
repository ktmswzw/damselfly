package com.damselfly.common.mybatis;

import org.apache.commons.lang.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * Created by vincent on 2014/10/13.
 */
@Component
@Aspect
public class MultipleDataSourceInterceptor  implements Serializable {
    /**
     * <b>function:</b> 动态设置数据源
     * @throws Exception
     */

    @Before("execution(* com.damselfly..impl.*ServiceImpl*.*(..)) || execution(* com.damselfly..mapper.*Mapper*.*(..))")
    public void dynamicSetDataSoruce(JoinPoint joinPoint) throws Exception {

        Class<?> clazz = joinPoint.getTarget().getClass();
        String className = clazz.getName();
        if (ClassUtils.isAssignable(clazz, Proxy.class)) {
            className = joinPoint.getSignature().getDeclaringTypeName();
        }
        String methodName = joinPoint.getSignature().getName();

        if (className.contains("Default")) {
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_DEFAULT);
        } else if (className.contains("Generator")) {
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERATOR);
        } else if (methodName.contains("Default")) {
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_DEFAULT);
        } else if (methodName.contains("Generator")) {
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERATOR);
        } else {
            CustomerContextHolder.clearCustomerType();
        }

    }
}
