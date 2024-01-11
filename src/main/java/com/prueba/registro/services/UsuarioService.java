package com.prueba.registro.services;

import com.prueba.registro.utils.BCryptUtils;
import com.prueba.registro.constants.Constantes;
import com.prueba.registro.models.Telefono;
import com.prueba.registro.models.Usuario;
import com.prueba.registro.repositories.UsuarioRepository;
import com.prueba.registro.repositories.TelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioService {
    @Value("${pattern.email}")
    public String patternEmail;

    @Value("${pattern.contrasena}")
    public String patternContrasena;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TelefonoRepository telefonoRepository;

    public ResponseEntity<Map<String, Object>> almacenarUsuario(Usuario usuario) throws Exception {
        Map<String, Object> response = new HashMap<>();
        ResponseEntity<Map<String, Object>> validacion;
        if(!validarDatosEntrada(usuario)){
            response.put(Constantes.MENSAJE, Constantes.ERROR_CONTRASENA);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        validacion = esUsuarioValido(usuario, response);
        if (validacion != null) {
            return validacion;
        }
        try {
            String hashedContrasena = BCryptUtils.hashedContrasena(usuario.getContrasena());
            usuario.setContrasena(hashedContrasena);
            Usuario usuariodb = usuarioRepository.save(usuario);
            for (Telefono tel : usuariodb.getTelefonos()) {
                tel.setUsuario(usuariodb);
                telefonoRepository.save(tel);
            }
            response.put(Constantes.MENSAJE, Constantes.USUARIO_CREADO);
            response.put(Constantes.USUARIO, usuariodb);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put(Constantes.MENSAJE, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private Boolean validarDatosEntrada(Usuario usuario) {
        return !usuario.getNombre().isEmpty() && usuario.getContrasena().matches(patternContrasena);
    }

    public ResponseEntity<Map<String, Object>> esUsuarioValido(Usuario usuario, Map<String, Object> response) {
        if(response.get(Constantes.MENSAJE) != null){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(!verificarCorreo(usuario)){
            response.put(Constantes.MENSAJE, Constantes.CORREO_FORMATO_INVALIDO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST) ;
        }

        Optional<Usuario> usuariodb = usuarioRepository.findByCorreo(usuario.getCorreo());
        if(usuariodb.isPresent()){
            response.put(Constantes.MENSAJE, Constantes.CORREO_DUPLICADO);
            return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private Boolean verificarCorreo(Usuario usuario) {
        Pattern pattern = Pattern.compile(this.patternEmail);
        Matcher matcher = pattern.matcher(usuario.getCorreo());
        return matcher.find();
    }

    public ResponseEntity<Map<String, Object>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ResponseEntity<Map<String, Object>> response = new ResponseEntity(usuarios, HttpStatus.OK);
        return response;
    }


    public ResponseEntity<Map<String, Object>> eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
        Map<String, Object> response = new HashMap<>();
        response.put(Constantes.MENSAJE, Constantes.ELIMINAR_USUARIO);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> actualizarUsuario(Long id, Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        Boolean validacion;
        if(!validarDatosEntrada(usuario)){
            response.put(Constantes.MENSAJE, Constantes.ERROR_CONTRASENA);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        validacion = verificarCorreo(usuario);
        if (!validacion) {
            response.put(Constantes.MENSAJE, Constantes.ERROR_CONTRASENA);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<Usuario> usuariodb = usuarioRepository.findById(id);
        if(usuariodb.isPresent()){
            usuariodb.get().setNombre(usuario.getNombre());
            String hashedContrasena = BCryptUtils.hashedContrasena(usuario.getContrasena());
            usuariodb.get().setContrasena(hashedContrasena);
            usuariodb.get().setCorreo(usuario.getCorreo());
            try{
                Usuario usuarioModificado = usuarioRepository.save(usuariodb.get());
                for(Telefono tel : usuario.getTelefonos()){
                    tel.setUsuario(usuarioModificado);
                    telefonoRepository.save(tel);
                }
                response.put(Constantes.MENSAJE, Constantes.USUARIO_MODIFICADO);
                response.put(Constantes.USUARIO, usuarioRepository.findById(usuarioModificado.getId()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put(Constantes.MENSAJE, e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        response.put(Constantes.MENSAJE, Constantes.USUARIO_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
