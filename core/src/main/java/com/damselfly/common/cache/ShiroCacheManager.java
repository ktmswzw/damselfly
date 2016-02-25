package com.damselfly.common.cache;

import org.apache.shiro.cache.Cache;

/**
 * custom shiro cache manager interface
 *
 * @author vincent
 */
public interface ShiroCacheManager {

    <K, V> Cache<K, V> getCache(String name);

    void destroy();

}
