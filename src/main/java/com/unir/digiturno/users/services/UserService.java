package com.unir.digiturno.users.services;

import java.util.List;
import java.util.Optional;

import com.unir.digiturno.users.models.entities.User;


public interface UserService {

    List<User> finAll();
    
    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update(User user, Long id);

    void remove(Long id);

    Optional<User> findByCorreo(String correo);

}
