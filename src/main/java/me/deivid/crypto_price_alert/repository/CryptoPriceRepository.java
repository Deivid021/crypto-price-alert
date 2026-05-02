package me.deivid.crypto_price_alert.repository;

import me.deivid.crypto_price_alert.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    @Query(value = """
    select id, price, symbol, date_current
    from crypto_price
    where symbol = :symbol
      and date_current::date = now()::date
    order by date_current
    limit 1
""", nativeQuery = true)
    CryptoPrice findFirstPriceToday(String symbol);

}