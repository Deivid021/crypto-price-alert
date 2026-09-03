package me.deivid.crypto_price_alert.service;

import jakarta.transaction.Transactional;
import me.deivid.crypto_price_alert.dto.UserAlertRequestDTO;
import me.deivid.crypto_price_alert.dto.UserAlertResponseDTO;
import me.deivid.crypto_price_alert.exception.UserAlertNotFoundException;
import me.deivid.crypto_price_alert.model.UserAlert;
import me.deivid.crypto_price_alert.repository.UserAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UserAlertResponseDTO> listar() {

        List<UserAlert> allUserAlert = repository.findAll();
        return allUserAlert.stream()
                           .map(UserAlertResponseDTO::from)
                           .toList();
    }

    public UserAlertResponseDTO findById(Long idUserAlert) {

        return repository.findById(idUserAlert)
                         .map(UserAlertResponseDTO::from)
                         .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));
    }

    public UserAlert editar(Long idUserAlert, UserAlertRequestDTO dto) {

        UserAlert usuarioExiste = repository.findById(idUserAlert)
                                                       .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));

        usuarioExiste.setEmail(dto.getEmail());
        usuarioExiste.setSymbol(dto.getSymbol());
        usuarioExiste.setAlertType(dto.getAlertType());
        usuarioExiste.setPriceLimit(dto.getPriceLimit());

        return repository.save(usuarioExiste);

    }

    @Transactional
    public void excluir(Long idUserAlert) {
        UserAlert usuarioExiste = repository.findById(idUserAlert)
                .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));

        repository.delete(usuarioExiste);
    }
}
