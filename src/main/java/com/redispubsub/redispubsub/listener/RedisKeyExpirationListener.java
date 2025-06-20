
package com.redispubsub.redispubsub.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisKeyExpirationListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString(); // This will be the key name directly
        log.info("Key expired: {}", expiredKey);
        // You can add your business logic here
    }
}
