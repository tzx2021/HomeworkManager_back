package sc.hqu.graduationdesign.homeworkmanager.provider;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 通用的缓存提供者对象接口
 * @author tzx
 * @date 2021-03-18 23:58
 */
public interface GenericCacheProvider {

    /**
     * 获取缓存
     * @param k     缓存的key
     * @return      缓存对象值
     */
    Object get(Object k);

    /**
     * 获取hash表中的缓存
     * @param k     hash表的key
     * @param hk    hash表中缓存值的key
     * @return      缓存对象值
     */
    Object getFromHash(Object k,Object hk);

    /**
     * 设置普通缓存
     * @param k     缓存key
     * @param v     缓存值
     */
    void set(Object k,Object v);

    /**
     * 设置超时缓存
     * @param k         缓存key
     * @param v         缓存值
     * @param timeout   超时时间
     * @param timeUnit  时间单位
     */
    void set(Object k, Object v, Long timeout, TimeUnit timeUnit);

    /**
     * key对应的值不存在时，设置该值
     * @param k     缓存的key
     * @param v     缓存值
     * @return      如果key对应的值存在，那么不设置值，返回false，如果key对应的值不存在，那么会设置值，返回true
     */
    Boolean setIfAbsent(Object k,Object v);

    /**
     * 超时的setIfAbsent
     * @param k         缓存的key
     * @param v         缓存值
     * @param timeout   超时时间
     * @param timeUnit  时间单位
     * @return          如果key对应的值存在，那么不设置值，返回false，如果key对应的值不存在，那么会设置值，返回true
     */
    Boolean setIfAbsent(Object k,Object v,Long timeout,TimeUnit timeUnit);

    /**
     * 设置hash表缓存
     * @param k     hash表缓存key
     * @param hk    hash表中值的key
     * @param hv    hash表中的缓存值
     */
    void setHash(Object k,Object hk,Object hv);

    /**
     * 将map中所有的值设置为一个完整的hash表，即批量设置hash表缓存
     * @param k         hash表的key
     * @param map       封装了缓存值的map
     */
    void setHash(Object k, Map<Object,Object> map);

    /**
     * 移除缓存中指定的key
     * @param keys      key数组
     */
    void remove(Object...keys);

    /**
     * 从hash表中移除缓存
     * @param k         hash缓存的key
     * @param hks       hash表中缓存值的key数组
     */
    void removeFromHash(Object k,Object... hks);

    /**
     * 为指定的key设置超时时间
     * @param k         缓存key
     * @param timeout   超时时间
     * @param timeUnit  时间单位
     */
    void setExpired(Object k, long timeout, TimeUnit timeUnit);

    /**
     * 获取某个key的超时时间
     * @param k         缓存key
     * @return          返回超时时间，如果key没有设置超时时间会返回null
     */
    Long getExpired(Object k);

    /**
     * 判断缓存是否存在指定的key
     * @param k     缓存key
     * @return      true时表示存在
     */
    Boolean hasKey(Object k);

}
