package com.prueba.registro.controllers;

import com.prueba.registro.models.Usuario;
import com.prueba.registro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Usuario usuario) throws Exception {
        HashMap<String, Object> resp = new HashMap<>();
        return usuarioService.almacenarUsuario(usuario);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCorreoUsuario(@PathVariable Long id, @RequestParam String correo){
        return usuarioService.actualizarCorreoUsuario(id,correo);
    }
}
