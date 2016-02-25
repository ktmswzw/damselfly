package com.damselfly.mapper;

import com.damselfly.entity.Reset;
import com.damselfly.entity.ResetCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetMapper {
    int countByExample(ResetCriteria example);

    int deleteByExample(ResetCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Reset record);

    int insertSelective(Reset record);

    List<Reset> selectByExample(ResetCriteria example);

    Reset selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Reset record, @Param("example") ResetCriteria example);

    int updateByExample(@Param("record") Reset record, @Param("example") ResetCriteria example);

    int updateByPrimaryKeySelective(Reset record);

    int updateByPrimaryKey(Reset record);
}