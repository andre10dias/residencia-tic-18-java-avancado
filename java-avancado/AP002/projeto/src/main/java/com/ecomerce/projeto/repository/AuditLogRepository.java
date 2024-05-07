package com.ecomerce.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.projeto.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
