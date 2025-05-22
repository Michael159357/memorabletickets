package org.example.pioneer.controller;

import org.example.pioneer.dto.UserDTO;
import org.example.pioneer.dto.UserProfileDTO;
import org.example.pioneer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pioneer/users")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtener información de un usuario por su ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Actualizar información de perfil del usuario
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UserProfileDTO userProfileDTO
    ) {
        UserDTO updatedUser = userService.updateUser(userId, userProfileDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Eliminar un usuario por su ID (solo para administradores)
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
