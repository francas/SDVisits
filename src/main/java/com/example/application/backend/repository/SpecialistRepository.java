package com.example.application.backend.repository;

import com.example.application.backend.entity.Specialist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {
    Specialist findByEmail(String email);
}