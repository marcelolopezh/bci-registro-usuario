package com.prueba.registro.repositories;

import com.prueba.registro.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario , Long> {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByCorreo(String correo);
    void deleteById(Long id);
    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);

}
