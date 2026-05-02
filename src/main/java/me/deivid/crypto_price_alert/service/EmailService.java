package me.deivid.crypto_price_alert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String mensagem) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("crypto.alertsmvp@gmail.com");
        mail.setTo("deividsestrensantos@gmail.com");
        mail.setSubject("Alerta Crypto");
        mail.setText(mensagem);

        mailSender.send(mail);
    }
}