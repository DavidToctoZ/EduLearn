package com.app.edulearn.repository;



import com.app.edulearn.model.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    void deleteByEmail(String email);
    AppUser findByEmail(String email);
    
}
