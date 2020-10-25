package com.app.edulearn.repository;

import com.app.edulearn.model.Icono;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconoRepo extends JpaRepository<Icono, Long>{
    
}

