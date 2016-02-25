package com.damselfly.service.core;

import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.SecurityPortal;
import com.damselfly.entity.SecurityPortalReport;

import java.util.List;

/**
 * Created by vincent on 2014/9/14.
 */
public interface PortalService {
    Page findByPage(Page page, SecurityPortal securityPortal);

    int countByExample(Page page,SecurityPortal securityPortal);

    SecurityPortal get(int id);

    List<SecurityPortalReport> customTypeSum(int state);

    List<SecurityPortalReport> customMonthSum(int state);

    List<SecurityPortalReport> serverRoom(int serverRoomId);

    List<SecurityPortalReport> investMonth();

    List<SecurityPortalReport> orderMonth();

    List<SecurityPortalReport> complaintMonth();

}
