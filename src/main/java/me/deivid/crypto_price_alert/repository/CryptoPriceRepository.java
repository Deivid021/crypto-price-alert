package me.deivid.crypto_price_alert.repository;

import me.deivid.crypto_price_alert.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {
}
