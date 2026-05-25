package me.deivid.crypto_price_alert.service;

import me.deivid.crypto_price_alert.dto.UserAlertRequestDTO;
import me.deivid.crypto_price_alert.model.UserAlert;
import me.deivid.crypto_price_alert.repository.UserAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAlertService {

    @Autowired
    private UserAlertRepository repository;

    public UserAlert salvar(UserAlertRequestDTO dto) {

        UserAlert alert = new UserAlert();

        alert.setEmail(dto.getEmail());
        alert.setSymbol(dto.getSymbol());
        alert.setPriceLimit(dto.getPriceLimit());
        alert.setAlertType(dto.getAlertType());

        return repository.save(alert);

    }
}
