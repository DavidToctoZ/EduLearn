package com.app.edulearn.controller;


import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import java.security.Principal;

import com.app.edulearn.model.AppUser;
import com.app.edulearn.services.UserService;
import com.app.edulearn.utils.EncryptedPasswordUtils;
import com.app.edulearn.utils.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
    @Autowired
    UserService userService;
    
    

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
     }


    @RequestMapping(value= "/registro", method = RequestMethod.GET)
    public String registro(Model model){
        model.addAttribute("userForm", new AppUser());
        
        return "Registro";
    }

    @RequestMapping(value="/registro", method = RequestMethod.POST)
    public String registrarUsuario(@ModelAttribute AppUser user, ModelMap model) throws Exception {
        model.addAttribute("userForm", user);
        
        System.out.println(user.getEmail());
        user.setEnabled(true);

        user.setEncryptedPassword(EncryptedPasswordUtils.encryptePassword(user.getEncryptedPassword()));
        
        boolean isCreated = userService.addUser(user);
        if(isCreated)
        { 
            return "ListaGrados"; 
        }
        else{
            model.addAttribute("userForm", new AppUser());
            model.addAttribute("error", "Error de creacion");
            return "Registro";
        }
        
    }

    @RequestMapping(value = "/grados", method = RequestMethod.GET)
    public String listaGrados() {
        return "ListaGrados";
     }

    @RequestMapping(value = "/cursos", method = RequestMethod.GET)
     public String listaCursos() {
         return "lista_de_cursos";
      }

    @RequestMapping(value = "/contacto", method = RequestMethod.GET)
      public String contacto() {
          return "contacto";
    }
    @RequestMapping(value = "/aritmetica5", method = RequestMethod.GET)
      public String aritmetica5() {
          return "aritmetica5";
    } 
    @RequestMapping(value = "/algebra5", method = RequestMethod.GET)
      public String algebra5() {
          return "algebra5";
    } 
    @RequestMapping(value = "/comunicacion5", method = RequestMethod.GET)
      public String comunicacion5() {
          return "comunicacion5";
    } 
    @RequestMapping(value = "/ingles5", method = RequestMethod.GET)
      public String ingles5() {
          return "ingles5";
    } 
    @RequestMapping(value = "/geometria5", method = RequestMethod.GET)
      public String geometria5() {
          return "geometria5";
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
