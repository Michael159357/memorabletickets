package org.example.pioneer.components;

import org.example.pioneer.domain.User;
import org.example.pioneer.enumrole.Role;
import org.example.pioneer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setLastName("Example"); // Asegúrate de establecer un valor válido aquí
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña segura
            admin.setRoles(List.of(Role.ROLE_USER, Role.ROLE_ADMIN));
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        }
    }
}
