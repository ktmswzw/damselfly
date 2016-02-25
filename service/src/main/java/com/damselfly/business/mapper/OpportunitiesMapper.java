package com.damselfly.business.mapper;

import com.damselfly.business.entity.Opportunities;
import com.damselfly.business.entity.OpportunitiesCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpportunitiesMapper {
    int countByExample(OpportunitiesCriteria example);

    int deleteByExample(OpportunitiesCriteria example);

    int deleteByPrimaryKey(Integer opportunitiesId);

    int insert(Opportunities record);

    int insertSelective(Opportunities record);

    List<Opportunities> selectByExample(OpportunitiesCriteria example);

    Opportunities selectByPrimaryKey(Integer opportunitiesId);

    int updateByExampleSelective(@Param("record") Opportunities record, @Param("example") OpportunitiesCriteria example);

    int updateByExample(@Param("record") Opportunities record, @Param("example") OpportunitiesCriteria example);

    int updateByPrimaryKeySelective(Opportunities record);

    int updateByPrimaryKey(Opportunities record);
}