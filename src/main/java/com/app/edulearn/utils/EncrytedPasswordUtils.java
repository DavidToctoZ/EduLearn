package com.app.edulearn.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {
    //Cambiar a encrypt cuando funcione
    public static String encrytePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args){
        String password = "123";
        String encrytedPassword = encrytePassword(password);
        System.out.println("Encrypte password: " + encrytedPassword);
    }
}
