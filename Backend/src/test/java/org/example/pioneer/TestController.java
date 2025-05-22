package org.example.pioneer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/security")
    public ResponseEntity<?> testSecurityContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return ResponseEntity.ok("Usuario autenticado: " + auth.getName() + ", Roles: " + auth.getAuthorities());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
    }
}
