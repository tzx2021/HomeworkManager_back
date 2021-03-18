package sc.hqu.graduationdesign.homeworkmanager.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis缓存提供者自动配置类
 * 如果当前运行的JVM中不包含{@link RedisOperations}的class文件时，该自动配置类将不会被加载
 * 这会与{@link org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration}的加载条件保持一致
 * 这里由于该starter模块仅在业务中使用，因此使用{@link PropertySource}注解提供单独的配置文件进行配置
 * @author tzx
 * @date 2021-03-18 23:45
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@PropertySource(value = {"classpath:application-redis.properties"}, encoding = "UTF-8")
public class RedisCacheProviderAutoConfigure {

    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        //使用Jackson2JsonRedisSerializer对对象进行序列化
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        //使用JsonRedisSerializer对value进行序列化
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);
        //使用StringRedisSerializer对key进行序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
