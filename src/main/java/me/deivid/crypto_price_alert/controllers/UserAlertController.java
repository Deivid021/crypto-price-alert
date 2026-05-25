package me.deivid.crypto_price_alert.controllers;

import me.deivid.crypto_price_alert.dto.UserAlertRequestDTO;
import me.deivid.crypto_price_alert.service.UserAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import me.deivid.crypto_price_alert.model.UserAlert;

@RestController
@RequestMapping("/alerts")
public class UserAlertController {

    @Autowired
    private UserAlertService service;

    @PostMapping
    public UserAlert criar(@RequestBody UserAlertRequestDTO dto) {
        return service.salvar(dto);
    }
}
