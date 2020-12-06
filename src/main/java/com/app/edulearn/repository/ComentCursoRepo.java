package com.app.edulearn.repository;

import com.app.edulearn.model.ComentCurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentCursoRepo extends JpaRepository<ComentCurso, Long>{
    
}
