package me.deivid.crypto_price_alert.model;

import jakarta.persistence.*;
import lombok.Data;
import me.deivid.crypto_price_alert.enums.AlertType;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "user_alert")
public class UserAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false)
    private Long id;

    @Column(
            name = "email",
            length = 254
    )
    private String email;

    @Column(
            name = "symbol",
            length = 10,
            nullable = false
    )
    private String symbol;

    @Column(
            name = "price_limit",
            precision = 10, scale = 2,
            nullable = false
    )
    private BigDecimal priceLimit;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "alert_type",
            length = 30,
            nullable = false
    )
    private AlertType alertType;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active = true;

}