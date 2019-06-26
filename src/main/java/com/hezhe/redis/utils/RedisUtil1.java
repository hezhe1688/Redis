package com.hezhe.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 贺哲
 * @2019-06-21 19:19
 */
public class RedisUtil1 {
    private static JedisPool pool;

    static {
        /**
         * 连接池Redis Pool基本配置信息
         */
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5); //最大连接数
        poolConfig.setMaxIdle(5);  //最大空闲数

        //连接池
        pool = new JedisPool("172.16.81.128", 6379);
    }

    /**
     * 获取Jedis
     * @return
     */
    public static Jedis getJedis(){
        Jedis jedis = pool.getResource();
        return jedis;
    }

    /**
     * 关闭Jedis
     * @param jedis
     */
    public static void closeJedis(Jedis jedis){
        jedis.close();
    }
}
