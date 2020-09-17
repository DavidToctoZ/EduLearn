package com.app.edulearn.controller;


import org.springframework.ui.Model;
import java.security.Principal;

import com.app.edulearn.utils.WebUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
  
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
     }


    @RequestMapping(value= "/Registro")
    public String registro(){
        return "Registro";
    }

    @RequestMapping(value = "/grados")
    public String listaGrados() {
        return "ListaGrados";
     }

     @RequestMapping(value = "/admin", method = RequestMethod.GET)
     public String adminPage(Model model, Principal principal) {
         User loginedUser = (User) ((Authentication) principal).getPrincipal();
  
         String userInfo = WebUtils.toString(loginedUser);
         model.addAttribute("userInfo", userInfo);
          
         return "adminPage";
     }

     @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
 
        // After user login successfully.
        String userName = principal.getName();
 
        System.out.println("User Name: " + userName);
 
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
 
        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
            String userInfo = WebUtils.toString(loginedUser);
 
            model.addAttribute("userInfo", userInfo);
 
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
 
        }
 
        return "403Page";
    }
}
