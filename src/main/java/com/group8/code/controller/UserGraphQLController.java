package com.group8.code.controller;

import com.group8.code.domain.User;
import com.group8.code.dto.UserDto;
import com.group8.code.dto.AuthDto;
import com.group8.code.service.UserService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;


import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@Validated
public class UserGraphQLController {
    @Autowired
    private UserService userService;

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('user/view','Ver Usuarios','Registrar Vehículo')")
    public List<User> users(){
        return userService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('user/view','Ver Usuarios')")
    public User user(@Argument String id){
        return userService.findById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('user/create','Registrar Usuario')")
    public User registerUser(@Argument @Valid UserDto userDto, @Argument @Valid AuthDto authDto) throws NoSuchAlgorithmException {
        User user = userService.findByUsername(authDto.getUsername());
        //System.out.println("-----register--- " + user);
        if(user == null){
            return userService.createUser(userDto,authDto);
        }else{
            throw  new RuntimeException("La cuenta de usuario ya existe");
        }
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('user/update','Ver Usuarios')")
    public User updateUser(@Argument String id,@Argument @Valid UserDto userDto){
        return userService.updateUser(id,userDto);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('user/delete','Ver Usuarios')")
    public User deleteUser(@Argument String id){
        return userService.deleteuser(id);
    }

    @MutationMapping
    public User login(@Argument AuthDto authDto) {
        return userService.login(authDto);
    }
}
