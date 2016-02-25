package com.damselfly.service.core.impl;


import com.damselfly.common.baseservice.BaseService;
import com.damselfly.service.core.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Set;


/**
 * Created by Vincent on 2015/4/22.
 */
@Service(value = "redisService")
@Transactional
@SuppressWarnings("unchecked")
public class RedisServiceImpl extends BaseService implements RedisService {

    private static String redisCode = "utf-8";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void del(final String... keys) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

/**
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

/**
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

/**
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

/**
     * @param key
     * @param value
     */
    @Override
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

/**
     * @param key
     * @return
     */

    @Override
    public String get(final String key) {
         String txt = (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes()), redisCode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
        return txt;
    }

/**
     * @param pattern
     * @return
     */
    @Override
    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

/**
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        boolean flag = (boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
            
        });
        return  flag;
    }

/**
     * @return
     */
    @Override
    public String flushDB() {
        String txt = (String)redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
        return txt;
    }

/**
     * @return
     */
    @Override
    public long dbSize() {
        long num = (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return  num;
    }

/**
     * @return
     */
    @Override
    public String ping() {
        String txt = (String)redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.ping();
            }
        });
        return txt;
    }

    @Override
    public void append(final String key, final String value) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.append(key.getBytes(), value.getBytes());
                return 1L;
            }
        });
    }
/*

    @Override
    public void set(String key,String type) {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        redisTemplate.opsForValue().set(key, type);
        connection.expire(SerializeUtil.serialize(key), expireTime);

//		System.out.println(redisTemplate.opsForSet().members(key));

//		des2.decrypt(des2.encrypt(key)))
//		redisTemplate.opsForSet().add("setKey4", "4");
//		System.out.println(redisTemplate.opsForSet().members("setKey1"));
//		redisTemplate.opsForSet().pop("setKey4");
//		System.out.println(redisTemplate.opsForSet().size("setKey4"));
    }
*/


}