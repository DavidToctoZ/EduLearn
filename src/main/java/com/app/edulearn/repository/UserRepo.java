package com.app.edulearn.repository;

import javax.transaction.Transactional;

import com.app.edulearn.model.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    @Modifying
    @Transactional
    void deleteByEmail(String email);
    AppUser findByEmail(String email);
    
}
