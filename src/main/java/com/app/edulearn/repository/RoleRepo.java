package com.app.edulearn.repository;

import com.app.edulearn.model.AppRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<AppRole, Long> {
    AppRole findByName(String name);
}
