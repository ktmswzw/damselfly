package com.damselfly.mapper;

import com.damselfly.entity.LogEntity;
import com.damselfly.entity.LogEntityCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int countByExample(LogEntityCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int deleteByExample(LogEntityCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int insert(LogEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int insertSelective(LogEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    List<LogEntity> selectByExample(LogEntityCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    LogEntity selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LogEntity record, @Param("example") LogEntityCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LogEntity record, @Param("example") LogEntityCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LogEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_log_entity
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LogEntity record);
}