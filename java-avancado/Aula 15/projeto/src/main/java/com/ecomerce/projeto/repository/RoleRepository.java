package com.ecomerce.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.projeto.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}