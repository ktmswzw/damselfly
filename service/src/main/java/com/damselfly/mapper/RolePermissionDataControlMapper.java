package com.damselfly.mapper;

import com.damselfly.entity.RolePermissionDataControl;
import com.damselfly.entity.RolePermissionDataControlCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionDataControlMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int countByExample(RolePermissionDataControlCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int deleteByExample(RolePermissionDataControlCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int insert(RolePermissionDataControl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int insertSelective(RolePermissionDataControl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    List<RolePermissionDataControl> selectByExample(RolePermissionDataControlCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    RolePermissionDataControl selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RolePermissionDataControl record, @Param("example") RolePermissionDataControlCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RolePermissionDataControl record, @Param("example") RolePermissionDataControlCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RolePermissionDataControl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_role_permission_data_control
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RolePermissionDataControl record);
}