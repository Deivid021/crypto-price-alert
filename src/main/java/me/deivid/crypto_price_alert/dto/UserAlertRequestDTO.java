package me.deivid.crypto_price_alert.dto;

import jakarta.persistence.*;
import lombok.Data;
import me.deivid.crypto_price_alert.enums.AlertType;

import java.math.BigDecimal;

@Data
public class UserAlertRequestDTO {

        private String email;
        private String symbol;
        private BigDecimal priceLimit;
        private AlertType alertType;

}
