package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.Grado;
import com.app.edulearn.model.GradoCurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoCursoRepo extends JpaRepository<GradoCurso, Long> {
    List<GradoCurso> findByGrado(Grado grado);
}
