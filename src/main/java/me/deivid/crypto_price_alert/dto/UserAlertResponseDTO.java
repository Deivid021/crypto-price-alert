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
        public UserAlertResponseDTO(UserAlert entity) {
                this(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getSymbol(),
                        entity.getPriceLimit(),
                        entity.getAlertType(),
                        entity.isActive()
                );
        }
}
