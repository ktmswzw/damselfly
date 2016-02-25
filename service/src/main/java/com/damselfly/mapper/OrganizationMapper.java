package com.damselfly.mapper;

import com.damselfly.entity.Organization;
import com.damselfly.entity.OrganizationCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int countByExample(OrganizationCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int deleteByExample(OrganizationCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int insert(Organization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int insertSelective(Organization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    List<Organization> selectByExample(OrganizationCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    Organization selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Organization record, @Param("example") OrganizationCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Organization record, @Param("example") OrganizationCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Organization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_organization
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Organization record);
}