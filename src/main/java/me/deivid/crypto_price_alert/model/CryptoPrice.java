package me.deivid.crypto_price_alert.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CryptoPrice")
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(precision = 20, scale = 8)
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime date_current;

    public CryptoPrice(){}
}
