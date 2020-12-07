package com.app.edulearn.services;

import com.app.edulearn.model.*;
import com.app.edulearn.repository.RoleRepo;
import com.app.edulearn.repository.UserRoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleService {
    @Autowired
    RoleRepo roleRepo;
    
    @Autowired
    UserRoleRepo userRoleRepo;


    public void  addUserRole(AppUser user){

        AppRole appRole =roleRepo.findByName("ROLE_USER");
        UserRole userRole = new UserRole();

        userRole.setAppRole(appRole);
        userRole.setAppUser(user);

        userRoleRepo.save(userRole);
    }
}
