package com.jy.common.utils;

import redis.clients.jedis.*;
import java.util.*;

public class TestForRedis
{
    public static void main(final String[] args) {
        System.out.println("----" + RedisUtils.get("name"));
        RedisUtils.set("test", "testValue");
        System.out.println("----" + RedisUtils.get("test"));
        System.out.println("----" + RedisUtils.type("test"));
        System.out.println("=========>: " + RedisUtils.hget("uid:1c64d82feae049b79f32cfcd39032773", "loginName"));
        System.out.println("=========>: " + RedisUtils.hget("uid:1c64d82feae049b79f32cfcd39032773", "name"));
        System.out.println("=========>: " + RedisUtils.hgetAll("uid:1c64d82feae049b79f32cfcd39032773"));
    }
    
    private static void stringTest(final Jedis jedis) {
        final String name = jedis.get("name");
        System.out.println(name);
        final Long del = jedis.del("name");
        System.out.println(del);
        final String string = jedis.get("name");
        System.out.println(string);
        final String mset = jedis.mset(new String[] { "k1", "v1", "k2", "v2" });
        System.out.println(mset);
        final List<String> mget = (List<String>)jedis.mget(new String[] { "k1", "k2" });
        for (final String string2 : mget) {
            System.out.println(string2);
        }
    }
    
    private static void hashTest(final Jedis jedis) {
        jedis.hset("h1", "name", "xuelianbo");
        jedis.hset("h1", "age", "11");
        jedis.hset("h1", "sex", "\u7537");
        final String hget = jedis.hget("h1", "name");
        System.out.println(hget);
        final Map<String, String> hgetAll = (Map<String, String>)jedis.hgetAll("h1");
        final Set<String> keySet = hgetAll.keySet();
        for (final String key : keySet) {
            System.out.println(String.valueOf(key) + ":" + hgetAll.get(key));
        }
        final Boolean hexists = jedis.hexists("h1", "name");
        System.out.println(hexists);
    }
    
    private static void listTest(final Jedis jedis) {
        jedis.lpush("L1", new String[] { "\u5c0f\u660e" });
        jedis.lpush("L1", new String[] { "\u5c0f\u5f3a" });
        jedis.lpush("L1", new String[] { "\u5c0f\u738b" });
        jedis.lpush("L1", new String[] { "\u5c0f\u521a" });
        final List<String> lrange = (List<String>)jedis.lrange("L1", 0L, -1L);
        for (final String one : lrange) {
            System.out.println(one);
        }
        jedis.lset("L1", 2L, "2");
        System.out.println(jedis.llen("L1"));
        jedis.lpop("L1");
        jedis.lpop("L1");
        jedis.lpop("L1");
        jedis.lpop("L1");
        jedis.lpop("L1");
        jedis.lpop("L1");
        System.out.println(jedis.llen("L1"));
    }
}
