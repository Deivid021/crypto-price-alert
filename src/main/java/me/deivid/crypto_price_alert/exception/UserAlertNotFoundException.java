package me.deivid.crypto_price_alert.exception;


public class UserAlertNotFoundException extends RuntimeException {

    private Long id;

    public Long getId() {
        return id;
    }

    public UserAlertNotFoundException(Long id) {
        super("Não encontrado campo: " + id);
        this.id = id;
    }
}
