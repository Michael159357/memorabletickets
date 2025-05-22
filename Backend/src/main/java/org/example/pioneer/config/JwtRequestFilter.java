package org.example.pioneer.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.pioneer.custom.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRequestFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            final String authorizationHeader = httpRequest.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7).trim(); // Asegúrate de eliminar los espacios extra
                System.out.println("Token recibido (sin espacios):" + jwt ); // Log del token recibido

                if (jwt.isEmpty()) {
                    throw new RuntimeException("Token JWT vacío");
                }

                // Decodificar el JWT y extraer el ID del usuario
                String username = jwtUtil.extractEmail(jwt);
                String userId = jwtUtil.extractUserId(jwt);

                System.out.println("Token JWT recibido: " + jwt);
                System.out.println("Correo extraído del JWT: " + username);
                System.out.println("ID extraído del JWT: " + userId);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        // Establecer el contexto de autenticación con el ID de usuario
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el token JWT: " + e.getMessage());
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido o expirado");
            return;
        }

        chain.doFilter(request, response);
    }
}
