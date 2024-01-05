package com.prueba.registro.services;

import com.prueba.registro.constants.Constantes;
import com.prueba.registro.dto.UsuarioLoginDTO;
import com.prueba.registro.dto.UsuarioLoginResponseDTO;
import com.prueba.registro.models.Usuario;
import com.prueba.registro.repositories.UsuarioRepository;
import com.prueba.registro.utils.BCryptUtils;
import com.prueba.registro.utils.JWTTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<Map<String, Object>> login(UsuarioLoginDTO usuarioLoginDTO){
        Map<String, Object> response = new HashMap<>();
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(
                usuarioLoginDTO.getCorreo());
        if(!usuario.isPresent() || !BCryptUtils.checkContrasena(usuarioLoginDTO.getContrasena(), usuario.get().getContrasena())){
            response.put(Constantes.MENSAJE, Constantes.AUTENTICACION_FALLIDA);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        Date date = new Timestamp(System.currentTimeMillis());
        String token = JWTTokenUtils.getJWTToken(usuario.get().getId() + " " + date);
        usuario.get().setToken(token);
        usuario.get().setActivo(true);
        usuario.get().setUltimoLogin(date);
        Usuario usuariodb = usuarioRepository.save(usuario.get());

        UsuarioLoginResponseDTO usuarioLoginResponseDTO = new UsuarioLoginResponseDTO();
        usuarioLoginResponseDTO.setId(usuariodb.getId());
        usuarioLoginResponseDTO.setCreado(usuariodb.getCreado().toString());
        usuarioLoginResponseDTO.setModificado(usuariodb.getModificado().toString());
        usuarioLoginResponseDTO.setUltimoLogin(usuariodb.getUltimoLogin().toString());
        usuarioLoginResponseDTO.setToken(usuariodb.getToken());
        usuarioLoginResponseDTO.setActivo(usuariodb.isActivo());

        response.put(Constantes.USUARIO, usuarioLoginResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
