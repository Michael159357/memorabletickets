package org.example.pioneer.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "jskdbasjdkasblidjqwpocnasjkndioqwjsjsIDWQJKNXSMOIABDASKDNASZXKJDBASDOBASDJAKSI";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    /**
     * Generar un token JWT para el usuario
     *
     * @param email    Correo del usuario (subject)
     * @param timezone Zona horaria del usuario (opcional, solo informativa)
     * @param userId   ID del usuario
     * @return Token JWT
     */
    public String generateToken(String email, String timezone, String userId) {
        Map<String, Object> claims = new HashMap<>();
        if (timezone != null) {
            claims.put("timezone", timezone); // Agrega la zona horaria al token
        }
        claims.put("userId", userId); // Agrega el ID del usuario al token

        // Log para verificar los datos antes de la creación del token
        System.out.println("Generando token con los siguientes datos:");
        System.out.println("Email: " + email);
        System.out.println("Timezone: " + timezone);
        System.out.println("User ID: " + userId);

        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        ZonedDateTime now = ZonedDateTime.now(); // Hora actual en UTC
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.plusHours(10).toInstant())) // Expira en 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // Log para verificar el token creado
        System.out.println("Token generado: " + token);
        return token;
    }

    /**
     * Validar si el token JWT ha expirado (basado en UTC)
     *
     * @param token Token JWT
     * @return true si el token ha expirado, false de lo contrario
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractClaims(token);
            Date expiration = claims.getExpiration();
            boolean expired = expiration.before(new Date());
            System.out.println("Token expira en: " + expiration + " | ¿Expirado?: " + expired);
            return expired;
        } catch (Exception e) {
            System.out.println("Error al verificar la expiración del token: " + e.getMessage());
            return true; // Considerar el token como expirado en caso de error
        }
    }

    /**
     * Validar el token JWT con UserDetails
     *
     * @param token       Token JWT
     * @param userDetails Detalles del usuario
     * @return true si el token es válido, false de lo contrario
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        boolean valid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        System.out.println("¿Token válido?: " + valid);
        return valid;
    }

    /**
     * Extraer el email (subject) del token
     *
     * @param token Token JWT
     * @return Email del usuario
     */
    public String extractEmail(String token) {
        Claims claims = extractClaims(token);
        String email = claims != null ? claims.getSubject() : null;
        System.out.println("Email extraído del token: " + email); // Log del email extraído
        return email;
    }

    /**
     * Extraer todos los claims del token
     *
     * @param token Token JWT
     * @return Claims del token
     */
    private Claims extractClaims(String token) {
        try {
            // Imprimir el token antes de procesarlo para asegurarse que no tiene espacios
            System.out.println("Token recibido (con posibles espacios): [" + token + "]");

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Token analizado:");
            System.out.println("Issued at: " + claims.getIssuedAt());
            System.out.println("Expires at: " + claims.getExpiration());
            return claims;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("Token expirado. Expiró en: " + e.getClaims().getExpiration());
            throw e; // Relanzar para manejarlo
        } catch (Exception e) {
            // Aquí se agregan logs adicionales para imprimir el token completo
            System.out.println("Error al analizar el token JWT. Token recibido: " + token);
            System.out.println("Error detallado: " + e.getMessage());
            throw new RuntimeException("Token JWT inválido");
        }
    }

    /**
     * Extraer el User ID del token
     */
    public String extractUserId(String token) {
        // Eliminar el prefijo "Bearer " si existe
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim(); // Eliminar "Bearer " y los espacios al principio
        }

        Claims claims = extractClaims(token);
        String userId = claims != null ? claims.get("userId", String.class) : null;
        System.out.println("User ID extraído: " + userId); // Log del userId extraído
        return userId; // Retorna el userId
    }

}
