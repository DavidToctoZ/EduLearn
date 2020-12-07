package com.app.edulearn.services;


import com.app.edulearn.repository.UserRepo;


import com.app.edulearn.model.AppUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;

    public boolean addUser(AppUser user)
    {

        boolean isCreated = false;
        if (user == null) { 
            System.out.println("Configuration is ok?");
			return isCreated;
        }
        
        if(verifyEmail(user)==false) {
           
            userRepo.save(user);
            

            return isCreated = true;
        }else{
            System.out.println("Ya existe usuario con el correo ingresado");
            return isCreated;
        }
        
        //userRepo.save(user);
    }
    
    public boolean verifyEmail(AppUser user)
    {
        AppUser userFind = userRepo.findByEmail(user.getEmail());
        if(userFind == null)
        {
            return false;
        }
        else
        {
            return true;
        }   
    }

    
}
