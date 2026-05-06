package me.deivid.crypto_price_alert.service;

import me.deivid.crypto_price_alert.model.BinanceDTO;
import me.deivid.crypto_price_alert.model.CryptoPrice;
import me.deivid.crypto_price_alert.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BinanceTask {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PriceAlertService priceAlertService;

    private final String URL = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";

    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void executeTask() {
        BinanceDTO resposta =  restTemplate.getForObject(URL, BinanceDTO.class);

        String symbol = resposta.getSymbol();
        BigDecimal price = new BigDecimal(resposta.getPrice());

        CryptoPrice firstPriceDay = cryptoPriceRepository.findFirstPriceToday(symbol);

        if (firstPriceDay != null) {

           BigDecimal variacao = priceAlertService
                        .calcularVariacao(firstPriceDay.getPrice(), price);

            System.out.println("Variação: % " + variacao);
            emailService.enviar("teste de email" + variacao);

        } else {
            System.out.println("Não há registro no dia de hoje, gravando no banco..");
        }

        CryptoPrice entity = new CryptoPrice();
        entity.setSymbol(symbol);
        entity.setPrice(price);

        cryptoPriceRepository.save(entity);

        System.out.println("Salvo no banco: " + symbol + " - " + price);

    }
}