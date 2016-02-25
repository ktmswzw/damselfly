package com.damselfly.entity;

import java.io.Serializable;

public class UserRole implements Serializable{
    /** 
	 * @description: 
	 * @version 1.0
	 * @author V
	 * @createDate 2014-1-19;下午10:49:32
	 */
	private static final long serialVersionUID = 6210106324595488049L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_user_role.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_user_role.priority
     *
     * @mbggenerated
     */
    private Integer priority;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_user_role.role_id
     *
     * @mbggenerated
     */
    private Long roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_user_role.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_user_role.id
     *
     * @return the value of security_user_role.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_user_role.id
     *
     * @param id the value for security_user_role.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_user_role.priority
     *
     * @return the value of security_user_role.priority
     *
     * @mbggenerated
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_user_role.priority
     *
     * @param priority the value for security_user_role.priority
     *
     * @mbggenerated
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_user_role.role_id
     *
     * @return the value of security_user_role.role_id
     *
     * @mbggenerated
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_user_role.role_id
     *
     * @param roleId the value for security_user_role.role_id
     *
     * @mbggenerated
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_user_role.user_id
     *
     * @return the value of security_user_role.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_user_role.user_id
     *
     * @param userId the value for security_user_role.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
}