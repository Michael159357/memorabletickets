package org.example.pioneer.controller;

import org.example.pioneer.config.JwtUtil;
import org.example.pioneer.domain.User;
import org.example.pioneer.dto.UserLoginDTO;
import org.example.pioneer.dto.UserRegisterDTO;
import org.example.pioneer.enumrole.Role;
import org.example.pioneer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pioneer/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Usuario registrado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error inesperado"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        var user = userService.findByEmail(userLoginDTO.getEmail());
        if (user == null || !passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
        }

        // Obtener la zona horaria del usuario (puedes modificar este enfoque según tus necesidades)
        String timezone = "America/Lima"; // Establecer la zona horaria predeterminada, o tomarla desde alguna fuente externa

        // Generar el token con el email, zona horaria y ID del usuario
        String token = jwtUtil.generateToken(user.getEmail(), timezone, user.getId().toString()); // ID del usuario añadido al token

        // Preparar la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Inicio de sesión exitoso");

        return ResponseEntity.ok(response);
    }



}
