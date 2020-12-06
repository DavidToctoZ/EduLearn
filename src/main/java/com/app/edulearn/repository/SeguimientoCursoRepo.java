package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.AppUser;
import com.app.edulearn.model.GradoCurso;
import com.app.edulearn.model.SeguimientoCurso;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoCursoRepo extends JpaRepository<SeguimientoCurso, Long> {
    List<SeguimientoCurso> findByAppUser(AppUser appUser);
    List<SeguimientoCurso> findByGradoCurso(GradoCurso gradoCurso);
}
