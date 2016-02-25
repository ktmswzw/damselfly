package com.damselfly.mapper;

import com.damselfly.entity.SecurityPortal;
import com.damselfly.entity.SecurityPortalCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPortalMapper {
    int countByExample(SecurityPortalCriteria example);

    int deleteByExample(SecurityPortalCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(SecurityPortal record);

    int insertSelective(SecurityPortal record);

    List<SecurityPortal> selectByExample(SecurityPortalCriteria example);

    SecurityPortal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SecurityPortal record, @Param("example") SecurityPortalCriteria example);

    int updateByExample(@Param("record") SecurityPortal record, @Param("example") SecurityPortalCriteria example);

    int updateByPrimaryKeySelective(SecurityPortal record);

    int updateByPrimaryKey(SecurityPortal record);
}