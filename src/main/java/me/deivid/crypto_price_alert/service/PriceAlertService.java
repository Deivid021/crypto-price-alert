package me.deivid.crypto_price_alert.service;

import me.deivid.crypto_price_alert.enums.AlertType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceAlertService {

    public BigDecimal calcularVariacao (BigDecimal inicial, BigDecimal atual) {
        return atual
                .subtract(inicial)
                .divide(inicial, 6, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public boolean deveNotificar(BigDecimal priceCurrent, BigDecimal priceLimit, AlertType alertType) {

        if (priceCurrent.compareTo(priceLimit) >= 0 && alertType == alertType.UP) {
            return true;
        } else if (priceCurrent.compareTo(priceLimit) <= 0 && alertType == alertType.DOWN)
            return true;
        else {
            return false;
        }
    }

}
