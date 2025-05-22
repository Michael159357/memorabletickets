package org.example.pioneer.components;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleValidator {

    // Verificar si el usuario tiene todos los roles requeridos
    public boolean hasAllRoles(Authentication authentication, List<String> requiredRoles) {
        System.out.println("Entrando a hasAllRoles...");

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("La autenticación es nula o no está autenticado.");
            return false;
        }

        // Extraer los roles del usuario autenticado
        Set<String> userRoles = authentication.getAuthorities()
                .stream()
                .map(auth -> {
                    String role = auth.getAuthority().replace("ROLE_", "");
                    System.out.println("Rol encontrado: " + role);
                    return role;
                })
                .collect(Collectors.toSet());

        System.out.println("Roles del usuario: " + userRoles);
        System.out.println("Roles requeridos: " + requiredRoles);

        // Verificar que el usuario tiene exactamente los roles requeridos
        boolean result = userRoles.size() == requiredRoles.size() && userRoles.containsAll(requiredRoles);
        System.out.println("Resultado de hasAllRoles: " + result);
        return result;
    }

    // Verificar si el usuario tiene únicamente un rol específico
    public boolean hasOnlyRole(Authentication authentication, String requiredRole) {
        System.out.println("Entrando a hasOnlyRole...");
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("La autenticación es nula o no está autenticado.");
            return false;
        }

        // Extraer los roles del usuario autenticado
        Set<String> userRoles = authentication.getAuthorities()
                .stream()
                .map(auth -> {
                    String role = auth.getAuthority().replace("ROLE_", "");
                    System.out.println("Rol encontrado: " + role);
                    return role;
                })
                .collect(Collectors.toSet());

        System.out.println("Roles del usuario: " + userRoles);
        System.out.println("Rol requerido: " + requiredRole);

        boolean result = userRoles.size() == 1 && userRoles.contains(requiredRole);
        System.out.println("Resultado de hasOnlyRole: " + result);
        return result;
    }
}

