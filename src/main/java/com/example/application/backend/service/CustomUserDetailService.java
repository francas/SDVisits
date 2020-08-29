package com.example.application.backend.service;

import com.example.application.backend.entity.Specialist;
import com.example.application.backend.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private SpecialistRepository specialistRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Specialist specialist = specialistRepository.findByEmail(username);
        if (specialist == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = User.withUsername(specialist.getEmail()).password(specialist.getPassword()).authorities("USER").build();
        return user;
    }
}