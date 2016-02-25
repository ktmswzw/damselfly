package com.damselfly.shiro.freemarker;

import org.apache.shiro.subject.Subject;

/**
 * Created by vincent on 2014/9/6.
 */
public class HasAnyPermissionTag  extends PermissionTag {
    private static final String PERMISSION_NAMES_DELIMETER = ",";

    /**
     * 检查是否拥有其中一个权限
     * @param permissionNames
     * @return
     */
    protected boolean showTagBody(String permissionNames) {
        boolean hasAnyPermission = false;
        Subject subject = getSubject();
        if (subject != null) {
            for (String permission : org.apache.commons.lang3.StringUtils.split(permissionNames,PERMISSION_NAMES_DELIMETER)) {
                if (isPermitted(permission.trim())) {
                    hasAnyPermission = true;
                    break;
                }
            }
        }

        return hasAnyPermission;
    }
}
