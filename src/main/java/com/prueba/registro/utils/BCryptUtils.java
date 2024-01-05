package com.prueba.registro.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptUtils {
    public static String hashedContrasena(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Boolean checkContrasena(String passwordHashed, String password){
        return BCrypt.checkpw(passwordHashed, password);
    }
}
