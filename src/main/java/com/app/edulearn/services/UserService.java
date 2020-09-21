package com.app.edulearn.services;

import com.app.edulearn.repository.UserRepo;
import com.app.edulearn.model.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;

    public void add(AppUser user)
    {
        if (user == null) { 
            System.out.println("Configuration is ok?");
			return;
		}
        userRepo.save(user);
    }


    
}
