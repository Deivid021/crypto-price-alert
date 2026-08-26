package me.deivid.crypto_price_alert.dto;

import me.deivid.crypto_price_alert.enums.AlertType;
import me.deivid.crypto_price_alert.model.UserAlert;

import java.math.BigDecimal;

public record UserAlertResponseDTO(
        Long id,
        String email,
        String symbol,
        BigDecimal priceLimit,
        AlertType alertType,
        boolean active
) {
        public static UserAlertResponseDTO from(UserAlert userAlert) {

                return new UserAlertResponseDTO(
                        userAlert.getId(),
                        userAlert.getEmail(),
                        userAlert.getSymbol(),
                        userAlert.getPriceLimit(),
                        userAlert.getAlertType(),
                        userAlert.isActive()
                );
        }
}
