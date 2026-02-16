package me.deivid.crypto_price_alert.model;

import lombok.Data;

@Data
public class BinanceDTO {

    private String symbol;
    private String price;

    public BinanceDTO() {}
}
