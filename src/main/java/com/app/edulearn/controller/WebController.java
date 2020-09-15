package com.app.edulearn.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
  
    @RequestMapping(value = "/")
    public String login() {
        return "login";
     }

     
}
