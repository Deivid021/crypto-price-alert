package me.deivid.crypto_price_alert.service;

import me.deivid.crypto_price_alert.dto.BinanceDTO;
import me.deivid.crypto_price_alert.model.CryptoPrice;
import me.deivid.crypto_price_alert.model.UserAlert;
import me.deivid.crypto_price_alert.repository.CryptoPriceRepository;
import me.deivid.crypto_price_alert.repository.UserAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.List;

@Component
public class BinanceTask {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    private UserAlertRepository userAlertRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PriceAlertService priceAlertService;

    private static final Logger logger =
            LoggerFactory.getLogger(BinanceTask.class);

    @Value("${spring.mail.username}")
    private String emailRemetenteSistema;

    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void executeTask() {

        logger.info("Iniciando consulta na Binance");

        List<UserAlert> alerts = userAlertRepository.findByActiveTrue();

        List<String> symbols = alerts.stream()
                                    .map(UserAlert::getSymbol)
                                    .distinct()
                                    .toList();

        for (String symbolUser : symbols) {

            String UrlApi = "https://api.binance.com/api/v3/ticker/price?symbol=" + symbolUser;
            BinanceDTO responseBinance = restTemplate.getForObject(UrlApi, BinanceDTO.class);

            BigDecimal priceCurrent = new BigDecimal(responseBinance.getPrice());
            String symbolCurrent = responseBinance.getSymbol();
            logger.info("Informações recebidas da Binance, Preço: {} e Moeda: {}", priceCurrent, symbolCurrent);

            alerts.stream()
                    .filter(alert -> alert.getSymbol().equals(symbolCurrent))
                    .forEach(alert -> {
                        if (priceAlertService.deveNotificar(priceCurrent, alert.getPriceLimit(), alert.getAlertType())) {

                            logger.warn(
                                    "Chamada função deveNotificar com o preço atual {}, preço limite: {} e tipo de alerta: {}",
                                    priceCurrent,
                                    alert.getPriceLimit(),
                                    alert.getAlertType()
                            );

                            emailService.disparaEmail(emailRemetenteSistema,
                                    alert.getEmail(),
                                    "Seu alerta para a moeda " + alert.getSymbol() + " bateu o preço definido de " + priceCurrent
                            );
                            logger.info("Chamada da função enviar para disparar o email");

                            alert.setActive(false);
                            userAlertRepository.save(alert);
                        }
                    });

            CryptoPrice entityCryptoPrice = new CryptoPrice();
            entityCryptoPrice.setSymbol(symbolCurrent);
            entityCryptoPrice.setPrice(priceCurrent);

            cryptoPriceRepository.save(entityCryptoPrice);
            logger.info(
                    "Salvo no banco a moeda: {} com o preço: {}",
                    symbolCurrent,
                    priceCurrent
            );

        }
    }
}