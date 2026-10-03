package com.app.publications.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * Modo mantenimiento: intercepta TODAS las peticiones y responde HTTP 503
 * con un JSON fijo. Para revertir, volver a desplegar desde la rama main.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MaintenanceFilter extends OncePerRequestFilter {

    private static final String MESSAGE =
            "El servicio api-java_publications está en mantenimiento. Por favor, intenta más tarde.";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String body = "{\"status\":\"maintenance\",\"message\":\"" + MESSAGE
                + "\",\"timestamp\":\"" + Instant.now() + "\"}";

        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Retry-After", "3600");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(body);
    }
}
