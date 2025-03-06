package com.tenpo.prueba.security.filtro;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class ContentCachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Solo envolver la respuesta si la URL coincide con la ruta deseada
        if (!request.getRequestURI().contains("/tenpo/api")) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(request, wrappedResponse);
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
    }
}
