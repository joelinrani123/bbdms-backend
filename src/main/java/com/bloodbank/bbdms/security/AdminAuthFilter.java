package com.bloodbank.bbdms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminAuthFilter extends OncePerRequestFilter {

    private final TokenStore tokenStore;

    public AdminAuthFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    // Endpoints under /api/admin/** that do NOT require a token
    private boolean isPublic(String path, String method) {
        if (path.equals("/api/admin/login")) return true;
        return "OPTIONS".equalsIgnoreCase(method);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (!path.startsWith("/api/admin/") || isPublic(path, request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        String token = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        if (!tokenStore.isValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Unauthorized. Please login again.\"}");
            return;
        }

        request.setAttribute("adminUsername", tokenStore.usernameFor(token));
        filterChain.doFilter(request, response);
    }
}
