package com.group8.code.service.impl;


import com.group8.code.dto.LoginUser;
import com.group8.code.dto.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Objects;

public class AuthServiceImpl {
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDto login(AuthDto user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("Inicio de sesión fallido");
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String id = loginUser.getUser().getId();

        return null;
    }
}
