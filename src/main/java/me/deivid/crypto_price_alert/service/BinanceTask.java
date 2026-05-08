package me.deivid.crypto_price_alert.service;

import me.deivid.crypto_price_alert.model.BinanceDTO;
import me.deivid.crypto_price_alert.model.CryptoPrice;
import me.deivid.crypto_price_alert.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger =
            LoggerFactory.getLogger(BinanceTask.class);

    private final String URL = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";

    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void executeTask() {

        logger.info("Iniciando consulta na Binance");

        BinanceDTO resposta =  restTemplate.getForObject(URL, BinanceDTO.class);

        logger.info("Preço recebido: {}", resposta.getPrice());

        String symbol = resposta.getSymbol();
        BigDecimal price = new BigDecimal(resposta.getPrice());

        CryptoPrice firstPriceDay = cryptoPriceRepository.findFirstPriceToday(symbol);

        logger.info("Primeiro preço diário recebido: {}: {}", symbol, price);

        if (firstPriceDay != null) {

           BigDecimal variacao = priceAlertService
                        .calcularVariacao(firstPriceDay.getPrice(), price);

           BigDecimal limite = new BigDecimal("1");

           if (priceAlertService.deveNotificar(variacao, limite)) {
               logger.warn(
                       "Alerta disparado para {} com variação de {}%",
                       symbol,
                       variacao
                );

               emailService.enviar("Variação: " + variacao);
               logger.info("Disparo via email enviado.");
           }

        } else {
            logger.info("Não há registro no dia de hoje, gravando registro no banco de dados.");
        }

        CryptoPrice entity = new CryptoPrice();
        entity.setSymbol(symbol);
        entity.setPrice(price);

        cryptoPriceRepository.save(entity);

        logger.info(
                "Salvo no banco a moeda: {} com o preço: {}",
                symbol,
                price
        );
    }
}