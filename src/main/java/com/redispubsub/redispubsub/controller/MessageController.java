
package com.redispubsub.redispubsub.controller;

import com.redispubsub.redispubsub.model.TickerPriceDataRequestModel;
import com.redispubsub.redispubsub.service.MessagePublisher;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageController {

    @Autowired
    private MessagePublisher messagePublisher;

/*    @Autowired
    private ChannelTopic channelTopic;*/

    @PostMapping("/publish")
    public String publishMessage(@Valid @RequestBody TickerPriceDataRequestModel tickerPriceDataRequestModel) {
        messagePublisher.publish("channelTopic", tickerPriceDataRequestModel);
        return "Message published!";
    }
}
