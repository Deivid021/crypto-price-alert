package me.deivid.crypto_price_alert.dto;

import lombok.Data;
import me.deivid.crypto_price_alert.enums.AlertType;
import me.deivid.crypto_price_alert.model.UserAlert;

import java.math.BigDecimal;

@Data
public class UserAlertResponseDTO {

        private Long id;
        private String email;
        private String symbol;
        private BigDecimal priceLimit;
        private AlertType alertType;
        private boolean active;

        public static UserAlertResponseDTO from(UserAlert userAlert) {
                UserAlertResponseDTO dto = new UserAlertResponseDTO();
                dto.setId(userAlert.getId());
                dto.setEmail(userAlert.getEmail());
                dto.setSymbol(userAlert.getSymbol());
                dto.setPriceLimit(userAlert.getPriceLimit());
                dto.setAlertType(userAlert.getAlertType());
                dto.setActive(userAlert.isActive());
                return dto;
        }
}
