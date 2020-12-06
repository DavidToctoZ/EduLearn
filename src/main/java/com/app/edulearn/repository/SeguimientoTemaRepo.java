package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.AppUser;
import com.app.edulearn.model.SeguimientoTema;
import com.app.edulearn.model.Tema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoTemaRepo extends JpaRepository<SeguimientoTema, Long> {
    List<SeguimientoTema> findByTema(Tema tema);

    List<SeguimientoTema> findByTemaAndAppUser(Tema tema, AppUser appUser);

    List<SeguimientoTema> findByAppUser(AppUser appUser);
}
