package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.Contenido;
import com.app.edulearn.model.Tema;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  ContenidoRepo extends JpaRepository<Contenido, Long>{
    List<Contenido> findByTemaOrderByOrdenMostrar(Tema tema);
}
