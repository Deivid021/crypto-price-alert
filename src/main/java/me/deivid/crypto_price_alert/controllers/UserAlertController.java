package me.deivid.crypto_price_alert.controllers;

import me.deivid.crypto_price_alert.dto.UserAlertRequestDTO;
import me.deivid.crypto_price_alert.dto.UserAlertResponseDTO;
import me.deivid.crypto_price_alert.service.UserAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import me.deivid.crypto_price_alert.model.UserAlert;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class UserAlertController {

    @Autowired
    private UserAlertService service;

    @PostMapping
    public UserAlert criar(@RequestBody UserAlertRequestDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<UserAlertResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public UserAlertResponseDTO findById(@PathVariable("id")Long id){
        return service.findById(id);
    }

}
