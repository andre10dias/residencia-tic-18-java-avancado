package com.ecomerce.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.projeto.controller.dto.ResetPasswordRequest;
import com.ecomerce.projeto.service.TokenService;

@RestController
public class PasswordResetController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();

        // Verifica se o token é válido
        String email = tokenService.getEmailByToken(token);
        if (email != null) {
            // Verifica a complexidade da nova senha
            if (isPasswordComplex(newPassword)) {
                // Se a nova senha for complexa, redefina a senha do usuário no banco de dados
                resetPasswordInDatabase(email, newPassword);
                // Invalida o token para que não possa ser reutilizado
                tokenService.invalidateToken(token);
            } else {
                // Senha não atende aos critérios de complexidade
                // Trate aqui o caso em que a senha não é complexa o suficiente
            }
        } else {
            // Trate aqui o caso em que o token é inválido
            // Por exemplo, retorne uma mensagem de erro ou status 404
        }
    }

    private void resetPasswordInDatabase(String email, String newPassword) {
        // Implemente a lógica para redefinir a senha do usuário no banco de dados
        // Este é apenas um exemplo, você precisa substituir isso com sua própria lógica
        System.out.println("Redefinindo a senha do usuário " + email + " para " + newPassword);
    }

    private boolean isPasswordComplex(String password) {
        // Implemente a lógica para verificar a complexidade da senha
        // Este é apenas um exemplo, você precisa substituir isso com sua própria lógica
        return !StringUtils.isEmpty(password) && password.length() >= 8;
    }
}


//@RestController
//public class PasswordResetController {
//
//    @Autowired
//    private TokenService tokenService;
//
//    @PostMapping("/reset-password")
//    public void resetPassword(@RequestBody ResetPasswordRequest request) {
//        String token = request.getToken();
//        String newPassword = request.getNewPassword();
//
//        // Verifica se o token é válido
//        String email = tokenService.getEmailByToken(token);
//        if (email != null) {
//            // Se o token é válido, redefina a senha do usuário no banco de dados
//            resetPasswordInDatabase(email, newPassword);
//            // Invalida o token para que não possa ser reutilizado
//            tokenService.invalidateToken(token);
//        } else {
//            // Trate aqui o caso em que o token é inválido
//            // Por exemplo, retorne uma mensagem de erro ou status 404
//        }
//    }
//
//    private void resetPasswordInDatabase(String email, String newPassword) {
//        // Implemente a lógica para redefinir a senha do usuário no banco de dados
//        // Este é apenas um exemplo, você precisa substituir isso com sua própria lógica
//        System.out.println("Redefinindo a senha do usuário " + email + " para " + newPassword);
//    }
//}
