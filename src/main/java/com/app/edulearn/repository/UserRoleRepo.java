package com.app.edulearn.repository;

import javax.transaction.Transactional;

import com.app.edulearn.model.AppUser;
import com.app.edulearn.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    @Modifying
    @Transactional
    void deleteByAppUser(AppUser appUser);
}
