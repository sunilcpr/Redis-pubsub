package com.redispubsub.redispubsub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisKeySetListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String payload = new String(message.getBody());
        String rawKey = channel.substring(channel.indexOf(":") + 1); // Extract "spy"
        log.info("Received raw key: {}", rawKey);
        log.info("Received message on channel {}: {}", channel, payload);
    }
}
