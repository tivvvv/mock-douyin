package com.tiv.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * EXISTS key
     *
     * @param key
     * @return
     */
    public boolean keyIsExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * TTL key
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * EXPIRE key seconds
     *
     * @param key
     * @param timeout
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * INCRBY key delta
     *
     * @param key
     * @param delta
     * @return
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * DECRBY key delta
     *
     * @param key
     * @param delta
     * @return
     */
    public long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * KEYS pattern
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * DEL key
     *
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * SET key value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * SET key value EX timeout
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * SET key value NX EX 60
     *
     * @param key
     * @param value
     */
    public void setnx60s(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value, 60, TimeUnit.SECONDS);
    }

    /**
     * SET key value NX
     *
     * @param key
     * @param value
     */
    public void setnx(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * GET key
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * MGET keys
     *
     * @param keys
     * @return
     */
    public List<String> mget(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * pipeline 批量查询
     *
     * @param keys
     * @return
     */
    public List<Object> batchGet(List<String> keys) {

        List<Object> result = redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection src = (StringRedisConnection) connection;

                for (String k : keys) {
                    src.get(k);
                }
                return null;
            }
        });
        return result;
    }

    /**
     * HSET key field value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }


    /**
     * HGET key field
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * HDEL key field [field ...]
     *
     * @param key
     * @param fields
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * HGETALL key
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * HINCRBY name key delta
     *
     * @param name
     * @param key
     * @param delta
     * @return
     */
    public long incrementHash(String name, String key, long delta) {
        return redisTemplate.opsForHash().increment(name, key, delta);
    }

    /**
     * HINCRBY name key (-delta)
     *
     * @param name
     * @param key
     * @param delta
     * @return
     */
    public long decrementHash(String name, String key, long delta) {
        delta = delta * (-1);
        return redisTemplate.opsForHash().increment(name, key, delta);
    }


    /**
     * LPUSH key value
     *
     * @param key
     * @param value
     * @return
     */
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * LPOP key
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * RPUSH key value
     *
     * @param key
     * @param value
     * @return
     */
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }
}