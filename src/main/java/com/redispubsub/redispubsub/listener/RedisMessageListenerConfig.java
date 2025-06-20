package com.redispubsub.redispubsub.listener;

import com.redispubsub.redispubsub.service.RedisKeySetListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisMessageListenerConfig {

    /*@Value("${redis.pubsub.channel}")
    private String pubSubChannel;*/

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListener messageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener, new PatternTopic("__keyspace@0__:*"));
        return container;
    }

    @Bean
    MessageListener messageListener() {
        return new RedisKeySetListener();
    }
}

