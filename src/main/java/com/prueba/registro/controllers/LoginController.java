package com.prueba.registro.controllers;

import com.prueba.registro.dto.UsuarioLoginDTO;
import com.prueba.registro.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO){
        return loginService.login(usuarioLoginDTO);
    }
}
