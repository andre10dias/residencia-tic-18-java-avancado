package com.recupera.senha.recuperaSenha.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailComponent {
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
	
    public String sendEmail(String to, String subject, String body) {
    	log.info("Enviando e-mail... para: " + to);
    	
    	try {
    		SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(username);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			emailSender.send(message);
			return "E-mail enviado com sucesso.";
		} 
    	catch (Exception e) {
			return "Falha ao enviar email: " + e.getMessage();
    	}
    }

}
