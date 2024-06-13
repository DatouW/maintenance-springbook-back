package com.group8.code.service.impl;

import com.group8.code.domain.Role;
import com.group8.code.domain.User;
import com.group8.code.dto.AuthDto;
import com.group8.code.dto.UserDto;
import com.group8.code.repository.RoleRepository;
import com.group8.code.repository.UserRepository;
import com.group8.code.service.UserService;
import com.group8.code.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public List<User> findAll() {
        System.out.println("here---all people");
        List<User> users = userRepository.findAll();
        return users.stream().map(this::addRoleInfo).collect(Collectors.toList());
    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            addRoleInfo(user);
            return user;
        } else {
            throw new RuntimeException("Id no existe");
        }
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        addRoleInfo(user);
        return user;
    }

    @Override
    public User createUser(UserDto userDto, AuthDto authDto) throws NoSuchAlgorithmException {
        System.out.println("create ------");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(authDto.getPassword());
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .position(userDto.getPosition())
                .phone(userDto.getPhone())
                .username(authDto.getUsername())
                .password(encode)
                .roleId(userDto.getRoleId())
                .build();
        System.out.println(user);
        return userRepository.save(user);
    }

    @Override
    public User login(AuthDto authDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("Inicio de sesión fallido");
        }

        User user = findByUsername(authDto.getUsername());

        final Map<String, Object> map = Map.of("id", user.getId(),
                "username", user.getUsername(),
                "roleId", user.getRole().getId(),
                "permissions", user.getRole().getPermissions());
        final String token = JwtUtil.genToken(map);
        user.setToken(token);
        System.out.println(user);
        return user;
    }

    @Override
    public User updateUser(String id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not existe"));

        if (user != null) {
            user = User.builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .address(userDto.getAddress())
                    .phone(userDto.getPhone())
                    .position(userDto.getPosition())
                    .roleId(userDto.getRoleId())
                    .build();
        }
        userRepository.save(user);
        addRoleInfo(user);
        return userRepository.save(user);
    }

    @Override
    public User deleteuser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
        userRepository.delete(user);
        return user;
    }

    private User addRoleInfo(User user) {
        if (user != null && user.getRoleId() != null) {
            Role role = roleRepository.findById(user.getRoleId()).orElse(null);
            user.setRole(role);
        }
        return user;
    }
}