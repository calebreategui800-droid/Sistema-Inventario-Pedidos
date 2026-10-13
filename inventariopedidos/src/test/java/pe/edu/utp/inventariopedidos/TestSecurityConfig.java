package pe.edu.utp.inventariopedidos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Solo se usa en los tests (vive en src/test).
 * Deja abiertas las rutas /api/** para que los tests puedan probar el CRUD
 * sin tener que iniciar sesion. La seguridad real (SecurityConfig en main)
 * no se modifica: sigue exigiendo token en la aplicacion en vivo.
 */
//@Configuration
public class TestSecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}