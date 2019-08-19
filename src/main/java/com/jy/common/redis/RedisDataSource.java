package com.jy.common.redis;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	ShardedJedis getRedisClient();

	void returnResource(ShardedJedis p0);

	void returnResource(ShardedJedis p0, boolean p1);
}
