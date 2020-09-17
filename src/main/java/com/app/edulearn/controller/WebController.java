package com.app.edulearn.controller;

import com.app.edulearn.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
  
    @RequestMapping(value = "/")
    public String login() {
        return "login";
     }

    @RequestMapping(value= "/Registro")
    public String registro(){
        return "Registro";
    }
}
