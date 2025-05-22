package org.example.pioneer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Para encriptar contraseñas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/pioneer/auth/**").permitAll()
                        .requestMatchers("/api/pioneer/files/upload**").permitAll() // Permitir rutas públicas para autenticación
                        .requestMatchers("/image/**", "/static/**").permitAll() // Permitir acceso público a recursos estáticos
                        .anyRequest().authenticated() // Requerir autenticación para todas las demás rutas
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT

        return http.build();
    }
}
