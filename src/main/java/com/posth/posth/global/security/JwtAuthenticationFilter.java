package com.posth.posth.global.security;

import com.posth.posth.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getRequestURI().equals("/auth/login")) {
            return true;
        } else if (request.getRequestURI().startsWith("/swagger-ui")) {
            return true;
        } else if (request.getRequestURI().startsWith("/v3/api-docs")) {
            return true;
        } else if (request.getRequestURI().equals("/member/signup")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        String loginId = jwtUtil.getLoginIdFromToken(token);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginId, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null || token.isBlank()) {
            throw new RuntimeException("엑세스 토큰이 없습니다");
        }

        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        throw new RuntimeException("토큰 형식이 올바르지 않습니다.");
    }
}
