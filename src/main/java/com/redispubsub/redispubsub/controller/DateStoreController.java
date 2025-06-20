
package com.redispubsub.redispubsub.controller;

import com.redispubsub.redispubsub.model.TickerPriceDataRequestModel;
import com.redispubsub.redispubsub.service.RedisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DateStoreController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/store")
    public String publishMessage(@Valid @RequestBody TickerPriceDataRequestModel tickerPriceDataRequestModel) {
        redisService.store(tickerPriceDataRequestModel);
        return "Message published!";
    }
}
