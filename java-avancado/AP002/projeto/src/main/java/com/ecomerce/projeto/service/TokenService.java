package com.ecomerce.projeto.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private Map<String, TokenInfo> tokenMap = new HashMap<>();
    
    private static final long TOKEN_EXPIRATION_TIME_MS = 30 * 60 * 1000; // 30 minutos em milissegundos

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        TokenInfo tokenInfo = new TokenInfo(email, System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MS);
        tokenMap.put(token, tokenInfo);
        return token;
    }

    public String getEmailByToken(String token) {
        TokenInfo tokenInfo = tokenMap.get(token);
        if (tokenInfo != null && tokenInfo.getExpirationTime() >= System.currentTimeMillis()) {
            return tokenInfo.getEmail();
        } else {
            // Token expirado ou inválido
            return null;
        }
    }

    public void invalidateToken(String token) {
        tokenMap.remove(token);
    }

    private static class TokenInfo {
        private String email;
        private long expirationTime;

        public TokenInfo(String email, long expirationTime) {
            this.email = email;
            this.expirationTime = expirationTime;
        }

        public String getEmail() {
            return email;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}
