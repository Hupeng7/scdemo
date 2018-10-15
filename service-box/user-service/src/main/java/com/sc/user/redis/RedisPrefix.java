package com.sc.user.redis;

import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author hp
 * @time 2018/10/15.
 */
public class RedisPrefix implements RedisCachePrefix {
    private final RedisSerializer serializer;
    private final String delimiter;

    public RedisPrefix() {
        this(":");
    }

    public RedisPrefix(String delimiter) {
        this.serializer = new StringRedisSerializer();
        this.delimiter = delimiter;
    }

    @Override
    public byte[] prefix(String cacheName) {
        return this.serializer.serialize(this.delimiter != null ?
                this.delimiter.concat(":").concat(cacheName).concat(":")
                : cacheName.concat(":"));
    }
}
