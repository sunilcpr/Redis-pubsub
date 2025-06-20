package com.redispubsub.redispubsub.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TickerPriceDataRequestModel {

    @NotBlank
    private String symbol;

    @NotNull
    private Date timestamp;

    @NotNull
    private Float highPrice;

    @NotNull
    private Float lowPrice;


}
