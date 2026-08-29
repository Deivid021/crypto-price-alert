package me.deivid.crypto_price_alert.dto;

public record ErrorResponseDTO(
        Integer status,
        String Message,
        Long Id
) {}
