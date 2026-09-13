package me.deivid.crypto_price_alert.service;

import jakarta.transaction.Transactional;
import me.deivid.crypto_price_alert.dto.UserAlertRequestDTO;
import me.deivid.crypto_price_alert.dto.UserAlertResponseDTO;
import me.deivid.crypto_price_alert.dto.UserAlertStatusDTO;
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

    public UserAlertResponseDTO salvar(UserAlertRequestDTO dto) {

        UserAlert alert = new UserAlert();

        alert.setEmail(dto.getEmail());
        alert.setSymbol(dto.getSymbol());
        alert.setPriceLimit(dto.getPriceLimit());
        alert.setAlertType(dto.getAlertType());

        UserAlert usuarioSalvo = repository.save(alert);

        return new UserAlertResponseDTO(usuarioSalvo);
    }

    public List<UserAlertResponseDTO> listar() {

        List<UserAlert> allUserAlert = repository.findAll();
        return allUserAlert.stream()
                .map(UserAlertResponseDTO::new)
                .toList();
    }

    public UserAlertResponseDTO findById(Long idUserAlert) {

        return repository.findById(idUserAlert)
                .map(UserAlertResponseDTO::new)
                .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));
    }

    @Transactional
    public UserAlertResponseDTO editar(Long idUserAlert, UserAlertRequestDTO dto) {

        UserAlert usuarioExiste = repository.findById(idUserAlert)
                .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));

        usuarioExiste.setEmail(dto.getEmail());
        usuarioExiste.setSymbol(dto.getSymbol());
        usuarioExiste.setAlertType(dto.getAlertType());
        usuarioExiste.setPriceLimit(dto.getPriceLimit());

        UserAlert usuarioSalvo = repository.save(usuarioExiste);

        return new UserAlertResponseDTO(usuarioSalvo);
    }

    @Transactional
    public void excluir(Long idUserAlert) {
        UserAlert usuarioExiste = repository.findById(idUserAlert)
                .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));

        repository.delete(usuarioExiste);
    }

    @Transactional
    public UserAlertResponseDTO editarStatus(Long idUserAlert, UserAlertStatusDTO dto) {

        UserAlert usuarioExiste = repository.findById(idUserAlert)
                .orElseThrow(() -> new UserAlertNotFoundException(idUserAlert));

        usuarioExiste.setActive(dto.isActive());

        UserAlert usuarioSalvo = repository.save(usuarioExiste);

        return new UserAlertResponseDTO(usuarioSalvo);
    }
}
