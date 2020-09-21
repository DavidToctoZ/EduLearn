package com.app.edulearn.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {
    //Cambiar a encrypt cuando funcione
    public static String encryptePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args){
        String password = "123";
        String encrytedPassword = encryptePassword(password);
        System.out.println("Encrypte password: " + encrytedPassword);
    }
}
