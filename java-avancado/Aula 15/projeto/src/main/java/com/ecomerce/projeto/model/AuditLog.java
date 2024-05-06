package com.ecomerce.projeto.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AuditLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    @NotNull(message = "EventName não pode ser nulo.") // Validação no nível da aplicação
    @Size(min = 5, max = 50, message = "EventName deve ter entre 5 e 50 characteres long")
    @Column(unique = true, nullable = false) // Restrições a nível de banco de dados
    private String eventName;
    
    @NotNull(message = "eventDescription não pode ser nulo.")
    private String eventDescription;
    
    @NotNull(message = "timestamp não pode ser nulo.")
    private Date timestamp;
    
    @NotNull(message = "userId não pode ser nulo.")
    private String userId;
    
    @NotNull(message = "affectedResource não pode ser nulo.")
    private String affectedResource;
    
    @NotNull(message = "origin não pode ser nulo.")
    private String origin;

}
