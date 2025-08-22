package com.unir.digiturno.users.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unir.digiturno.users.models.entities.User;
import com.unir.digiturno.users.response.ApiResponse;
import com.unir.digiturno.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

   @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> list() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de usuarios", 2000, service.finAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> show(@PathVariable Long id) {
        return service.findById(id)
                .<ResponseEntity<ApiResponse<?>>>map(user ->
                        ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", 2000, user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado", 4040, null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody User user) {
        try {
            User nuevo = service.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Usuario creado", 2010, nuevo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), 4000, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody User user, @PathVariable Long id) {
        return service.update(user, id)
                .<ResponseEntity<ApiResponse<?>>>map(u ->
                        ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado", 2001, u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado", 4041, null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> remove(@PathVariable Long id) {
        Optional<User> o = service.findById(id);
        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado", 2002, null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, "Usuario no encontrado", 4042, null));
    }
}

