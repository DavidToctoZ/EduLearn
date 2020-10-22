package com.app.edulearn.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.app.edulearn.model.AppUser;
import com.app.edulearn.model.Curso;

import com.app.edulearn.repository.CursoRepo;
import com.app.edulearn.repository.GradoCursoRepo;
import com.app.edulearn.repository.GradoRepo;
import com.app.edulearn.services.CursoService;
import com.app.edulearn.services.UserRoleService;
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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;
    
    @Autowired
    GradoRepo gradoRepo;

    @Autowired
    CursoRepo cursoRepo;

    @Autowired
    CursoService cursoService;
    
    @Autowired
    GradoCursoRepo gradoCursoRepo;
    //ACTIVAR 
    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        
        return "login";
     }*/
     
     @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            //TOMAR EN CUENTA - PARA NOMBRE DE USUARIO
            System.out.println(request.getUserPrincipal().getName());
            return "redirect:/prueba";
        }
        return "redirect:/grados";
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
            userRoleService.addUserRole(user); 
            return "redirect:/default";  
        }
        else{
            model.addAttribute("userForm", new AppUser());
            model.addAttribute("error", "Error de creacion");
            return "Registro";
        }
        
    }


    //CAMBIAR A /grados
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listaGrados(Model model) {
        model.addAttribute("grados", gradoRepo.findAll());
        return "PaginaGrados";
     }

     @RequestMapping(value = "/prueba", method = RequestMethod.GET)
    public String prueba() {
        return "PaginaAdmin";
     }

    @RequestMapping(value = "/cursos")
     public String listaCursos(@RequestParam String buscar, Model model) {
        
        List<Curso> cursos = cursoService.encontrarCursosHabilitados(buscar);
        List<Curso> cursosProx = cursoService.encontrarCursosDeshabilitados(buscar);

        
        
        if(cursos == null && cursosProx == null){

            return "CursosGradoVacio";
        }
        
        if(cursos.isEmpty() && !cursosProx.isEmpty()){
            
            model.addAttribute("cursosProximos", cursosProx);
            return "CursosGradoParciaProx";
        }
        if(!cursos.isEmpty() && cursosProx.isEmpty()){
            model.addAttribute("cursos", cursos);
            return "CursosGradoParciaDisp";
        }
        System.out.println(cursos.size());
        System.out.println(cursos.isEmpty());
        System.out.println(cursosProx.size());
        System.out.println(cursosProx.isEmpty());
        model.addAttribute("cursosProximos", cursosProx);
        model.addAttribute("cursos", cursos);
        return "CursosGradoTodo";
      }

    @RequestMapping(value = "/contacto", method = RequestMethod.GET)
      public String contacto() {
          return "contacto1";
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
