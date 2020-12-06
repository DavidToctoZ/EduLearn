package com.app.edulearn.repository;

import java.util.List;

import com.app.edulearn.model.AppUser;
import com.app.edulearn.model.Grado;
import com.app.edulearn.model.Matricula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepo extends JpaRepository<Matricula, Long>{
    List<Matricula> findByAppUser(AppUser appUser);
    List<Matricula> findByAppUserAndGrado(AppUser appUser, Grado grado);

}
