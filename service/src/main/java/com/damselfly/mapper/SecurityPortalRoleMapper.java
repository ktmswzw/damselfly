package com.damselfly.mapper;

import com.damselfly.entity.SecurityPortalRole;
import com.damselfly.entity.SecurityPortalRoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPortalRoleMapper {
    int countByExample(SecurityPortalRoleCriteria example);

    int deleteByExample(SecurityPortalRoleCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(SecurityPortalRole record);

    int insertSelective(SecurityPortalRole record);

    List<SecurityPortalRole> selectByExample(SecurityPortalRoleCriteria example);

    SecurityPortalRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SecurityPortalRole record, @Param("example") SecurityPortalRoleCriteria example);

    int updateByExample(@Param("record") SecurityPortalRole record, @Param("example") SecurityPortalRoleCriteria example);

    int updateByPrimaryKeySelective(SecurityPortalRole record);

    int updateByPrimaryKey(SecurityPortalRole record);
}