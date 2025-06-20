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
public class RedisService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Store data in redis
     *
     * @param tickerPriceDataRequestModel {@link TickerPriceDataRequestModel}
     */
    public void store(TickerPriceDataRequestModel tickerPriceDataRequestModel) {
        try {
            Map<String, TickerPriceDataRequestModel> wrapper = new HashMap<>();
            wrapper.put(tickerPriceDataRequestModel.getSymbol(), tickerPriceDataRequestModel);
            // Convert to JSON
            String json = objectMapper.writeValueAsString(wrapper);
            // store data in redis
            redisTemplate.opsForValue().set(tickerPriceDataRequestModel.getSymbol(), json);
        } catch (JsonProcessingException e) {
            log.error("Error while publishing tickerPriceDataRequestModel: {}", e.getMessage());
        }
    }
}
