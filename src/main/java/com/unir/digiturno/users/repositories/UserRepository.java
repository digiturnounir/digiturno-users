package com.unir.digiturno.users.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.unir.digiturno.users.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
    
    Optional<User> findByCorreo(String correo);

}

