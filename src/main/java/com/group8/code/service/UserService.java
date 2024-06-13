package com.group8.code.service;

import com.group8.code.domain.User;
import com.group8.code.dto.AuthDto;
import com.group8.code.dto.UserDto;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    User findByUsername(String username);

    User createUser(UserDto userDto, AuthDto authDto) throws NoSuchAlgorithmException;

    User login(AuthDto authDto);

    User updateUser(String id, UserDto userDto);

    User deleteuser(String id);
}