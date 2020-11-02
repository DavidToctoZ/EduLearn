package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.Dinamica;
import com.app.edulearn.model.Tema;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DinamicaRepo extends JpaRepository<Dinamica, Long>{
    List<Dinamica> findByTema(Tema tema);
    Dinamica findByDinamicaId(Long dinamicaId);
}
