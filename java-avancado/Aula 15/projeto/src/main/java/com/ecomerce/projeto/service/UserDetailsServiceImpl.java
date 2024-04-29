package com.ecomerce.projeto.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security
                .core.userdetails.User(user.getEmail(), user.getSenha(),
                true, true, true,
                true, getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

}
