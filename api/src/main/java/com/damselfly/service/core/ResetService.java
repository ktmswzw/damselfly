package com.damselfly.service.core;

import com.damselfly.common.mybatis.Page;
import com.damselfly.entity.Reset;
import java.util.List;

/**
 * Created by v on 2014/8/30.
 */
public interface ResetService {
    void update(Reset reset);
    void save(Reset reset);
    List<Reset> find(Page page,Reset reset);
}
