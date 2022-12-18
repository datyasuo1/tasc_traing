package com.example.smart_shop.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.smart_shop.utils.JWTUtil;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String[] IGNORE_PATH = {"/api/v1/login", "/api/v1/register", "/api/token/refresh","/api/v1/product","/api/v1/category","/api/v1/brand"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // nếu client gửi requet đến 1 trong các url được bỏ qua thì không cần xử lí
        String requestPath = request.getServletPath();
        if (Arrays.asList(IGNORE_PATH).contains(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        // trường hợp không có request header theo format cần thiết
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            // cho qua (không có dấu kiểm duyệt)
            filterChain.doFilter(request, response);
        }

        // lấy token từ request header
        try {
            // Remove chữ Bearer
            String token = authorizationHeader.replace("Bearer", "").trim();

            // dịch ngược JWT
            DecodedJWT decodedJWT = JWTUtil.getDecodedJwt(token);

            // Lấy thông tin username
            String username = decodedJWT.getSubject();
            String role = decodedJWT.getClaim(JWTUtil.ROLE_CLAIM_KEY).asString();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> errors = new HashMap<>();
            errors.put("message", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new Gson().toJson(errors));
        }
    }
}