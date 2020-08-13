package com.hzit.proxy.utils;

import redis.clients.jedis.Jedis;

public class RedisUtil {
    private static Jedis jedis;
    static {
        jedis = new Jedis("192.168.1.185",6379);
    }

    /**
     *
     * @param key
     * @param value
     * @param expire 过期时间 单位 秒
     * @return
     */
    public static String set(String key, String value, int expire) {

        String set = jedis.set(key, value);
        jedis.expire(key, expire);

        return set;
    }

    /**
     * 通过key获取 value
     * @param key
     * @return
     */
    public static String get(String key) {
        String value = jedis.get(key);
        return value;
    }
}
