package com.redispubsub.redispubsub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redispubsub.redispubsub.model.TickerPriceDataRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MessagePublisher {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, TickerPriceDataRequestModel tickerPriceDataRequestModel) {
        try {
            Map<String, TickerPriceDataRequestModel> wrapper = new HashMap<>();
            wrapper.put(tickerPriceDataRequestModel.getSymbol(), tickerPriceDataRequestModel);
            // Convert to JSON
            String json = objectMapper.writeValueAsString(wrapper);
            // store data in redis
            redisTemplate.opsForValue().set(tickerPriceDataRequestModel.getSymbol(), json);
            // Publish to Redis channels
            //redisTemplate.convertAndSend(channel, json);
        } catch (JsonProcessingException e) {
            log.error("Error while publishing tickerPriceDataRequestModel: {}", e.getMessage());
        }
    }
}
