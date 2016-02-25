package com.damselfly.business.mapper;

import com.damselfly.business.entity.Test;
import com.damselfly.business.entity.TestCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    int countByExample(TestCriteria example);

    int deleteByExample(TestCriteria example);

    int deleteByPrimaryKey(Integer testId);

    int insert(Test record);

    int insertSelective(Test record);

    List<Test> selectByExample(TestCriteria example);

    Test selectByPrimaryKey(Integer testId);

    int updateByExampleSelective(@Param("record") Test record, @Param("example") TestCriteria example);

    int updateByExample(@Param("record") Test record, @Param("example") TestCriteria example);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}