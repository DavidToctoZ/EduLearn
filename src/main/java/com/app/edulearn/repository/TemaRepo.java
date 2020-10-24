package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.GradoCurso;
import com.app.edulearn.model.Tema;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepo extends JpaRepository<Tema, Long>{
    List<Tema> findByGradoCurso(GradoCurso gradoCurso);
    Tema findByTemaId(Long temaId);
}
