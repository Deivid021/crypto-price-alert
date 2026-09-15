package me.deivid.crypto_price_alert.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class PriceCacheService {

    private static final String PREFIXO_PRECO = "price:";
    private static final String SUFIXO_CURRENT = ":time_current:";

    private final StringRedisTemplate redisTemplate;

    public PriceCacheService (StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void savePrice(String symbol, BigDecimal price) {
        redisTemplate.opsForValue().set(PREFIXO_PRECO + symbol, price.toString());
        redisTemplate.opsForValue().set(PREFIXO_PRECO + symbol + SUFIXO_CURRENT, Instant.now().toString());
    }

    public Optional<BigDecimal> findPrice(String symbol) {

        String priceCurrent = redisTemplate.opsForValue().get(PREFIXO_PRECO + symbol);
        Optional<BigDecimal> priceCurrentOptional = Optional.ofNullable(priceCurrent).map(BigDecimal::new);
        return priceCurrentOptional;
    }

    public Optional<Instant> findLastCurrency(String symbol) {

        String timeCurrent = redisTemplate.opsForValue().get(PREFIXO_PRECO + symbol + SUFIXO_CURRENT);
        Optional<Instant> timeCurrentOptional = Optional.ofNullable(timeCurrent).map(Instant::parse);
        return timeCurrentOptional;
    }

}
