package com.redispubsub.redispubsub.config;

import com.redispubsub.redispubsub.listener.RedisKeyExpirationListener;
import com.redispubsub.redispubsub.listener.RedisKeySetListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * Redis connection factory
     *
     * @return {@link RedisConnectionFactory}
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * RedisTemplate
     *
     * @param connectionFactory {@link RedisConnectionFactory}
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    /**
     * Redis container
     *
     * @param connectionFactory     {@link RedisConnectionFactory}
     * @param setListenerAdapter    {@link  MessageListenerAdapter}
     * @param expireListenerAdapter {@link MessageListenerAdapter}
     * @return {@link RedisMessageListenerContainer}
     */
    @Bean
    RedisMessageListenerContainer container(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter setListenerAdapter,
            MessageListenerAdapter expireListenerAdapter
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // Listen for SET events
        container.addMessageListener(setListenerAdapter, new PatternTopic("__keyspace@0__:*"));
        // Listen for Expired events
        container.addMessageListener(expireListenerAdapter, new PatternTopic("__keyevent@0__:expired"));
        return container;
    }

    /**
     * Redis set key listener adapter
     *
     * @param listener {@link RedisKeySetListener}
     * @return {@link MessageListenerAdapter}
     */
    @Bean
    MessageListenerAdapter setListenerAdapter(RedisKeySetListener listener) {
        return new MessageListenerAdapter(listener);
    }

    /**
     * Redis expiry listener adapter
     *
     * @param listener {@link RedisKeyExpirationListener}
     * @return {@link MessageListenerAdapter}
     */
    @Bean
    MessageListenerAdapter expireListenerAdapter(RedisKeyExpirationListener listener) {
        return new MessageListenerAdapter(listener);
    }
}
