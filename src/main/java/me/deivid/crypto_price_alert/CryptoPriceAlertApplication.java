package me.deivid.crypto_price_alert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoPriceAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoPriceAlertApplication.class, args);
	}

}