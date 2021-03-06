package com.damselfly.mapper;

import com.damselfly.entity.Module;
import com.damselfly.entity.ModuleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int countByExample(ModuleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int deleteByExample(ModuleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int insert(Module record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int insertSelective(Module record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    List<Module> selectByExample(ModuleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    Module selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Module record, @Param("example") ModuleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Module record, @Param("example") ModuleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Module record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_module
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Module record);
}