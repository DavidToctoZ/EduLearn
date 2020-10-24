package com.app.edulearn.repository;

import com.app.edulearn.model.Curso;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {
    Curso findByName(String name);
    Curso findByCursoId(Long cursoId);
}
