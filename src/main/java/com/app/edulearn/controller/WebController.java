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

    //Aritmetica ---------------------------------------------------------------

    @RequestMapping(value = "/aritmetica5", method = RequestMethod.GET)
      public String aritmetica5() {
          return "aritmetica5";
    }

    @RequestMapping(value = "/ari_numdecimal5", method = RequestMethod.GET)
      public String ari_numdecimal5() {
          return "ari_numdecimal5";
    }
    
    @RequestMapping(value = "/ari_adiccion5", method = RequestMethod.GET)
      public String ari_adiccion5() {
          return "ari_adiccion5";
    }
    @RequestMapping(value = "/ari_sustraccion5", method = RequestMethod.GET)
      public String ari_sustraccion5() {
          return "ari_sustraccion5";
    }
    @RequestMapping(value = "/ari_multiplicacion5", method = RequestMethod.GET)
      public String ari_multiplicacion5() {
          return "ari_multiplicacion5";
    }
    @RequestMapping(value = "/ari_numprimcomp5", method = RequestMethod.GET)
      public String ari_numprimcomp5() {
          return "ari_numprimcomp5";
    }    
    //Fin Aritmetica ---------------------------------------------------------------

    // Geometria --------------------------------------------------
    @RequestMapping(value = "/geometria5", method = RequestMethod.GET)
      public String geometria5() {
          return "geometria5";
    } 

    @RequestMapping(value = "/geo_elementos5", method = RequestMethod.GET)
      public String geo_elementos5() {
          return "geo_elementos5";
    } 

    @RequestMapping(value = "/geo_segmentos5", method = RequestMethod.GET)
      public String geo_segmentos5() {
          return "geo_segmentos5";
    } 

    
    @RequestMapping(value = "/geo_punto5", method = RequestMethod.GET)
      public String geo_punto5() {
          return "geo_punto5";
    } 
    @RequestMapping(value = "/geo_plano5", method = RequestMethod.GET)
      public String geo_plano5() {
          return "geo_plano5";
    } 

    @RequestMapping(value = "/geo_angulo5", method = RequestMethod.GET)
      public String geo_angulos5() {
          return "geo_angulo5";
    } 
    // Fin Geometria --------------------------------------------------

    
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
