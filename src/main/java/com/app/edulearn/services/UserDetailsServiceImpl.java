package com.app.edulearn.services;

import java.util.ArrayList;
import java.util.List;


import com.app.edulearn.repository.UserRepo;
import com.app.edulearn.dao.AppRoleDAO;
import com.app.edulearn.model.AppUser;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AppRoleDAO roleDAO;

   

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        AppUser appUser = userRepo.findByEmail(email);
        if (appUser == null) {
            System.out.println("Email not found! " + email);
            throw new UsernameNotFoundException("Email " + email + " was not found in the database");
        }
        
        System.out.println("Found User: " + appUser);
 
        // [ROLE_USER, ROLE_ADMIN,..]
        
        List<String> roleNames = this.roleDAO.getRoleNames(appUser.getUserId());
       
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                System.out.println(role);
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        
        UserDetails userDetails = (UserDetails) new User(appUser.getEmail(), appUser.getEncryptedPassword(), grantList);
        
        return userDetails;
    }

}
