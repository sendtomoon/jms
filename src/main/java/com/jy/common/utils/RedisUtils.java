package com.jy.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class RedisUtils {

	private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

	private static ShardedJedisPool shardedJedisPool = null;

	static {
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new ByteConverter(null), Byte.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new ShortConverter(null), Short.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);

		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			log.info("[redis.properties] Redis配置文件未找到!");
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		poolConfig.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive").trim()));
		poolConfig.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle").trim()));
		poolConfig.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait").trim()));
		poolConfig.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow").trim()));
		poolConfig.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn").trim()));

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo = new JedisShardInfo(bundle.getString("redis.host").trim(),
				Integer.valueOf(bundle.getString("redis.port").trim()));
		jedisShardInfo.setConnectionTimeout(Integer.valueOf(bundle.getString("redis.timeout").trim()));
		jedisShardInfo.setPassword(bundle.getString("redis.pass").trim());
		shards.add(jedisShardInfo);

		shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
	}

	/**
	 * 获取ShardedJedis
	 * 
	 * @return ShardedJedis
	 */
	public static ShardedJedis getShardedJedis() {
		return shardedJedisPool.getResource();
	}

	/**
	 * close jedis
	 * 
	 * @param shardedJedis
	 */
	public static void close(ShardedJedis shardedJedis) {
		if (null != shardedJedis)
			shardedJedis.close();
	}

	/**
	 * returnResource
	 */
	public static void close() {
		if (null != shardedJedisPool)
			shardedJedisPool.close();
	}

	/**
	 * 设置单个值
	 * 
	 * @param key
	 * @param value
	 * @return String
	 */
	public static String set(String key, String value) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getShardedJedis();
			if (null != shardedJedis) {
				result = shardedJedis.set(key, value);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取单个值
	 * 
	 * @param key
	 * @return String
	 */
	public static String get(String key) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getShardedJedis();
			if (null != shardedJedis) {
				result = shardedJedis.get(key);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return boolean
	 */
	public static Boolean exists(String key) {
		Boolean result = false;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getShardedJedis();
			if (null != shardedJedis) {
				result = shardedJedis.exists(key);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if(null != shardedJedis){				
				shardedJedis.close();
			}
		}
		return result;
	}

	/**
	 * 查询key的值类型
	 * 
	 * @param key
	 * @return String
	 */
	public static String type(String key) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (null != shardedJedis) {
			try {
				result = shardedJedis.type(key);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	/**
	 * 在某段时间后实现
	 * 
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static Long expire(String key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (null != shardedJedis) {
			try {
				result = shardedJedis.expire(key, seconds);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	/**
	 * 在某个时间点失效
	 * 
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static Long expireAt(String key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (null != shardedJedis) {
			try {
				result = shardedJedis.expireAt(key, unixTime);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static Long ttl(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (null != shardedJedis) {
			try {
				result = shardedJedis.ttl(key);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static boolean setbit(String key, long offset, boolean value) {

		boolean result = false;
		ShardedJedis shardedJedis = getShardedJedis();
		if (null != shardedJedis) {
			try {
				result = shardedJedis.setbit(key, offset, value);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				shardedJedis.close();
			}
		}
		return result;
	}

	public static boolean getbit(String key, long offset) {
		ShardedJedis shardedJedis = getShardedJedis();
		boolean result = false;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getbit(key, offset);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static long setrange(String key, long offset, String value) {
		ShardedJedis shardedJedis = getShardedJedis();
		long result = 0;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.setrange(key, offset, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String getrange(String key, long startOffset, long endOffset) {
		ShardedJedis shardedJedis = getShardedJedis();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getrange(key, startOffset, endOffset);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String getSet(String key, String value) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getSet(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long setnx(String key, String value) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.setnx(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String setex(String key, int seconds, String value) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.setex(key, seconds, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long decrBy(String key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.decrBy(key, integer);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long decr(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.decr(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long incrBy(String key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.incrBy(key, integer);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long incr(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.incr(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long append(String key, String value) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.append(key, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String substr(String key, int start, int end) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.substr(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hset(String key, String field, String value) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hset(key, field, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String hget(String key, String field) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hget(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hsetnx(String key, String field, String value) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hsetnx(key, field, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String hmset(String key, Map<String, String> hash) {
		String result = null;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = getShardedJedis();
			if (shardedJedis == null) {
				return result;
			}
			result = shardedJedis.hmset(key, hash);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if(null!=shardedJedis){				
				shardedJedis.close();
			}
		}
		return result;
	}

	public static List<String> hmget(String key, String... fields) {
		List<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hmget(key, fields);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hincrBy(String key, String field, long value) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hincrBy(key, field, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean hexists(String key, String field) {
		Boolean result = false;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hexists(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long del(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.del(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hdel(String key, String field) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hdel(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long hlen(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hlen(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> hkeys(String key) {
		Set<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hkeys(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> hvals(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hvals(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Map<String, String> hgetAll(String key) {
		Map<String, String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hgetAll(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	// ================list ====== l表示 list或 left, r表示right====================
	public static Long rpush(String key, String string) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.rpush(key, string);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long lpush(String key, String string) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lpush(key, string);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long llen(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.llen(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> lrange(String key, long start, long end) {
		List<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String ltrim(String key, long start, long end) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.ltrim(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lindex(String key, long index) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lindex(key, index);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lset(String key, long index, String value) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lset(key, index, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long lrem(String key, long count, String value) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lrem(key, count, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String lpop(String key) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lpop(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String rpop(String key) {
		String result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.rpop(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	// return 1 add a not exist value ,
	// return 0 add a exist value
	public static Long sadd(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.sadd(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> smembers(String key) {
		Set<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.smembers(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long srem(String key, String member) {
		ShardedJedis shardedJedis = getShardedJedis();

		Long result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.srem(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String spop(String key) {
		ShardedJedis shardedJedis = getShardedJedis();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.spop(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long scard(String key) {
		ShardedJedis shardedJedis = getShardedJedis();
		Long result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.scard(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Boolean sismember(String key, String member) {
		ShardedJedis shardedJedis = getShardedJedis();
		Boolean result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.sismember(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String srandmember(String key) {
		ShardedJedis shardedJedis = getShardedJedis();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.srandmember(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zadd(String key, double score, String member) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrange(String key, int start, int end) {
		Set<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrem(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrem(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Double zincrby(String key, double score, String member) {
		Double result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zincrby(key, score, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrank(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zrevrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrevrank(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<String> zrevrange(String key, int start, int end) {
		Set<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrevrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrangeWithScores(String key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Long zcard(String key) {
		Long result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zcard(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Double zscore(String key, String member) {
		Double result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zscore(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> sort(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.sort(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static List<String> sort(String key, SortingParams sortingParameters) {
		List<String> result = null;
		ShardedJedis shardedJedis = getShardedJedis();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.sort(key, sortingParameters);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Jedis getShard(String key) {
		ShardedJedis shardedJedis = getShardedJedis();
		Jedis result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getShard(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static JedisShardInfo getShardInfo(String key) {
		ShardedJedis shardedJedis = getShardedJedis();
		JedisShardInfo result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getShardInfo(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static String getKeyTag(String key) {
		ShardedJedis shardedJedis = getShardedJedis();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getKeyTag(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Collection<JedisShardInfo> getAllShardInfo() {
		ShardedJedis shardedJedis = getShardedJedis();
		Collection<JedisShardInfo> result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getAllShardInfo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Collection<Jedis> getAllShards() {
		ShardedJedis shardedJedis = getShardedJedis();
		Collection<Jedis> result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getAllShards();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public static Map<String, String> objectToHash(Object obj) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				if (!property.getName().equals("class")) {
					if (property.getReadMethod().invoke(obj) != null) {
						map.put(property.getName(), "" + property.getReadMethod().invoke(obj));
					}
				}
			}
			return map;
		} catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T hashToObject(Map<?, ?> map, Class c) {
		if (BeanUtil.isNull(map)) {
			return null;
		}
		try {
			Object obj = c.newInstance();
			hashToObject(map, obj);
			return (T) obj;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void hashToObject(Map<?, ?> map, Object obj) {
		try {
			BeanUtils.populate(obj, (Map) map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
