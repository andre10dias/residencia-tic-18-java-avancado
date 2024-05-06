package com.ecomerce.projeto.repository;


import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecomerce.projeto.model.AuditLog;

public class AuditLogRepositoryTest {
	
	@Autowired
    private AuditLogRepository repository;

    @Test
    public void testSaveAuditLog() {
        AuditLog log = new AuditLog();
        log.setEventName("LoginAttempt");
        log.setEventDescription("User login attempt");
        log.setTimestamp(new Date());
        log.setUserId("user1");
        log.setAffectedResource("LoginService");
        log.setOrigin("127.0.0.1");

        AuditLog savedLog = repository.save(log);
        assertThat(savedLog).isNotNull();
        assertThat(savedLog.getId()).isNotNull();
    }

}
