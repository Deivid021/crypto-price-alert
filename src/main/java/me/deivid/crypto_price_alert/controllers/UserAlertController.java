package me.deivid.crypto_price_alert.controllers;

import me.deivid.crypto_price_alert.dto.UserAlertRequestDTO;
import me.deivid.crypto_price_alert.dto.UserAlertResponseDTO;
import me.deivid.crypto_price_alert.dto.UserAlertStatusDTO;
import me.deivid.crypto_price_alert.service.UserAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import me.deivid.crypto_price_alert.model.UserAlert;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class UserAlertController {

    @Autowired
    private UserAlertService service;

    @PostMapping
    public UserAlertResponseDTO criar(@RequestBody UserAlertRequestDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<UserAlertResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public UserAlertResponseDTO findById(@PathVariable("id")Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public UserAlertResponseDTO editar(@PathVariable("id") Long id, @RequestBody UserAlertRequestDTO dto){
        return service.editar(id, dto);
    }

    @PatchMapping("/{id}")
    public UserAlertResponseDTO editarStatus(@PathVariable("id") Long id, @RequestBody UserAlertStatusDTO dto) {
        return service.editarStatus(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
