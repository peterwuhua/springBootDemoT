package cn.core.framework.utils.rediscache;

import java.util.Set;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class SpringRedisClientImpl  {
    private RedisTemplate<String, Object> template;
    private JedisConnectionFactory jedisConnectionFactory;

    public void setKey(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public Object getKey(String key) {
        return template.opsForValue().get(key);
    }

    public void incr(String key) {
        template.opsForValue().increment(key, 1);
    }

    public void lPush(String key, String value) {
        template.opsForList().leftPush(key, value);
    }

    public boolean checkKey(String key) {
        return template.hasKey(key);
    }

    public Object lIndex(String key) {
        return template.opsForList().index(key, 0);
    }

    public Long llength(String key) {
        return template.opsForList().size(key);
    }

    public String lpop(String key) {

        return (String) template.opsForList().leftPop(key);
    }

    public Set<String> getKeys(String pattern) {
        return template.keys(pattern);
    }

    public void flushAll() {
        Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
        jedis.flushAll();
        jedis.close();
    }
}


