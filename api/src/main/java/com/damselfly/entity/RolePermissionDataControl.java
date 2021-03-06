package com.damselfly.entity;

import java.io.Serializable;

public class RolePermissionDataControl implements Serializable{
    /** 
	 * @description: 
	 * @version 1.0
	 * @author V
	 * @createDate 2014-1-19;下午10:50:05
	 */
	private static final long serialVersionUID = 1907639940115005015L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_role_permission_data_control.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_role_permission_data_control.data_control_id
     *
     * @mbggenerated
     */
    private Long dataControlId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_role_permission_data_control.role_permission_id
     *
     * @mbggenerated
     */
    private Long rolePermissionId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_role_permission_data_control.id
     *
     * @return the value of security_role_permission_data_control.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_role_permission_data_control.id
     *
     * @param id the value for security_role_permission_data_control.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_role_permission_data_control.data_control_id
     *
     * @return the value of security_role_permission_data_control.data_control_id
     *
     * @mbggenerated
     */
    public Long getDataControlId() {
        return dataControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_role_permission_data_control.data_control_id
     *
     * @param dataControlId the value for security_role_permission_data_control.data_control_id
     *
     * @mbggenerated
     */
    public void setDataControlId(Long dataControlId) {
        this.dataControlId = dataControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_role_permission_data_control.role_permission_id
     *
     * @return the value of security_role_permission_data_control.role_permission_id
     *
     * @mbggenerated
     */
    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_role_permission_data_control.role_permission_id
     *
     * @param rolePermissionId the value for security_role_permission_data_control.role_permission_id
     *
     * @mbggenerated
     */
    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }
    
    private DataControl dataControl;

	public DataControl getDataControl() {
		return dataControl;
	}

	public void setDataControl(DataControl dataControl) {
		this.dataControl = dataControl;
	}
    
    
}