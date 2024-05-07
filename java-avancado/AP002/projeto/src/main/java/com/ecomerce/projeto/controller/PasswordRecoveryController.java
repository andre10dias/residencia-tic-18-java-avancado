package com.ecomerce.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.projeto.service.TokenService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
public class PasswordRecoveryController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/recover-password")
    public void recoverPassword(@RequestBody String email) {
        // Verifica se o e-mail está registrado (exemplo: você pode fazer isso com seu serviço de usuário)
        if (isEmailRegistered(email)) {
            // Gera um token único para o e-mail
            String token = tokenService.generateToken(email);

            // Envia o e-mail com o link de recuperação contendo o token
            sendRecoveryEmail(email, token);
        } else {
            // Trate aqui o caso em que o e-mail não está registrado
            // Por exemplo, você pode retornar uma mensagem de erro ou status 404
        }
    }

    private boolean isEmailRegistered(String email) {
        // Implemente a lógica para verificar se o e-mail está registrado
        // Este é apenas um exemplo, você pode substituir por sua própria lógica
        // Por exemplo, consultando um banco de dados de usuários
        return true;
    }

    private void sendRecoveryEmail(String email, String token) {
        String recoveryLink = "http://seusite.com/recover?token=" + token; // URL para a página de recuperação com o token

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Recuperação de senha");
            helper.setText("Olá,\n\nVocê solicitou a recuperação da sua senha. "
                    + "Por favor, clique no link abaixo para cadastrar uma nova senha:\n\n"
                    + recoveryLink + "\n\nAtenciosamente,\nSua Empresa");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Trate aqui qualquer exceção de envio de e-mail
            e.printStackTrace();
        }
    }
}