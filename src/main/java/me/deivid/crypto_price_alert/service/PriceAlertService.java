package me.deivid.crypto_price_alert.service;

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

    public boolean deveNotificar(BigDecimal variacao, BigDecimal limite) {
        return variacao.abs().compareTo(limite) >= 0;
    }

}
