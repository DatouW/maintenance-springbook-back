package com.group8.code.filter;

import com.group8.code.domain.User;
import com.group8.code.repository.UserRepository;
import com.group8.code.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        User user;
        List<SimpleGrantedAuthority> authorities;
        try{
            Map<String, Object> tokenMap = JwtUtil.parseToken(token);
            String username = (String) tokenMap.get("username");
            user = userRepository.findByUsername(username).orElse(null);
            if(Objects.isNull(user)){
                throw new RuntimeException("Usuario no logueado");
            }
            List<String> permissions = (List<String>)tokenMap.get("permissions");
            authorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Token Invalido");
        }

        //System.out.println("filter .. " + user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);

    }
}
