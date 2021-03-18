package sc.hqu.graduationdesign.homeworkmanager.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis的缓存提供者对象
 * @author tzx
 * @date 2021-03-18 23:58
 */
public class RedisCacheProvider implements GenericCacheProvider{

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public Object get(Object k) {
        return redisTemplate.opsForValue().get(k);
    }

    @Override
    public Object getFromHash(Object k, Object hk) {
        return redisTemplate.opsForHash().get(k, hk);
    }

    @Override
    public void set(Object k, Object v) {
        redisTemplate.opsForValue().set(k, v);
    }

    @Override
    public void set(Object k, Object v, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(k, v,timeout,timeUnit);
    }

    @Override
    public Boolean setIfAbsent(Object k, Object v) {
        return redisTemplate.opsForValue().setIfAbsent(k, v);
    }

    @Override
    public Boolean setIfAbsent(Object k, Object v, Long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(k, v, timeout, timeUnit);
    }

    @Override
    public void setHash(Object k, Object hk, Object hv) {
        redisTemplate.opsForHash().put(k, hk, hv);
    }

    @Override
    public void setHash(Object k, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(k, map);
    }

    @Override
    public void remove(Object... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    @Override
    public void removeFromHash(Object k, Object... hks) {
        redisTemplate.opsForHash().delete(k,hks);
    }

    @Override
    public void setExpired(Object k, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(k, timeout, timeUnit);
    }

    @Override
    public Long getExpired(Object k) {
        return redisTemplate.getExpire(k);
    }

    @Override
    public Boolean hasKey(Object k) {
        return redisTemplate.hasKey(k);
    }

    public RedisTemplate<Object,Object> getRedisTemplate(){
        return redisTemplate;
    }

}
