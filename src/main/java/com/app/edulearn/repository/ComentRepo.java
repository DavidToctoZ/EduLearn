package com.app.edulearn.repository;

import com.app.edulearn.model.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentRepo extends JpaRepository<Comentario, Long>{
    
}

