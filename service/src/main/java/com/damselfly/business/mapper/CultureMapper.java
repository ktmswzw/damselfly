package com.damselfly.business.mapper;

import com.damselfly.business.entity.Culture;
import com.damselfly.business.entity.CultureCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CultureMapper {
    int countByExample(CultureCriteria example);

    int deleteByExample(CultureCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Culture record);

    int insertSelective(Culture record);

    List<Culture> selectByExample(CultureCriteria example);

    Culture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Culture record, @Param("example") CultureCriteria example);

    int updateByExample(@Param("record") Culture record, @Param("example") CultureCriteria example);

    int updateByPrimaryKeySelective(Culture record);

    int updateByPrimaryKey(Culture record);
}