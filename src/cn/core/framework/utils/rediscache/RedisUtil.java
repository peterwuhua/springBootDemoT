package cn.core.framework.utils.rediscache;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * redis cache 工具类
 * 
 */
public final class RedisUtil {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(RedisUtil.class);
	private RedisTemplate<Serializable, Object> redisTemplate;
   /* @Autowired
    private JedisConnectionFactory jedisConnectionFactory;*/

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void incr(String key) {
		redisTemplate.opsForValue().increment(key, 1);
    }

    public void lPush(String key, String value) {
    	redisTemplate.opsForList().leftPush(key, value);
    }
    
    public Object lIndex(String key) {
        return redisTemplate.opsForList().index(key, 0);
    }

    public Long llength(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public String lpop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    public Set<Serializable> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

   /* public void flushAll() {
        Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
        jedis.flushAll();
        jedis.close();
    }*/

	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
