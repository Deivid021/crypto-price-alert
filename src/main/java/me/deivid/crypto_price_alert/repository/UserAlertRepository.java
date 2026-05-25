package me.deivid.crypto_price_alert.repository;

import me.deivid.crypto_price_alert.model.UserAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserAlertRepository extends JpaRepository<UserAlert, Long>  {

    List<UserAlert> findByActiveTrue();
}
