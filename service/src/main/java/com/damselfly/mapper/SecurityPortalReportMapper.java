package com.damselfly.mapper;

import com.damselfly.entity.SecurityPortalReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityPortalReportMapper {
    List<SecurityPortalReport> customTypeSum(@Param("state") Integer state);
    List<SecurityPortalReport> customMonthSum(@Param("state") Integer state);
    List<SecurityPortalReport> serverRoom(@Param("serverRoomId") Integer serverRoomId);
    List<SecurityPortalReport> investMonth();
    List<SecurityPortalReport> orderMonth();
    List<SecurityPortalReport> complaintMonth();
}