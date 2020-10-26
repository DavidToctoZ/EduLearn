package com.app.edulearn.repository;

import com.app.edulearn.model.Contacto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface contactoRepo extends JpaRepository<Contacto, Long> {
    
}
