package com.prueba.registro.repositories;

import com.prueba.registro.models.Telefono;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TelefonoRepository extends CrudRepository<Telefono, Long> {
}
