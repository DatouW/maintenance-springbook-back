package com.group8.code.service.impl;

import com.group8.code.domain.User;
import com.group8.code.dto.LoginUser;
import com.group8.code.repository.RoleRepository;
import com.group8.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if(Objects.isNull(user)){
            throw new RuntimeException("Usuario no existente o contraseña incorrecta.");
        }
        List<String> permissions = roleRepository.findById(user.getRoleId()).orElse(null).getPermissions();
        return new LoginUser(user,permissions);
    }
}
