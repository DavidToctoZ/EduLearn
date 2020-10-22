package com.app.edulearn.repository;

import com.app.edulearn.model.Grado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepo extends JpaRepository<Grado, Long> {
    
}
