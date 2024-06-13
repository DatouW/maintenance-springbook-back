package com.group8.code.controller;


import com.group8.code.domain.Role;
import com.group8.code.dto.RoleDto;
import com.group8.code.service.RoleService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;


import java.util.List;

@Controller
@Validated
public class RoleGraphQLController {
    @Autowired
    private RoleService roleService;

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('role/view','Ver Roles','Registrar Usuario')")
    public List<Role> roles(){
        return roleService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('role/view','Ver Roles')")
    public Role role(@Argument String id){
        return roleService.findById(id);
    }


    @MutationMapping
    @PreAuthorize("hasAnyAuthority('role/create','Registrar Roles')")
    public Role createRole(@Argument @Valid RoleDto roleDto){
//            System.out.println(roleDto);
        Role role = roleService.findByName(roleDto.getName());
        if(role == null){
            return roleService.create(roleDto);
        }else{
            throw  new RuntimeException("El rol ya existe");
        }
    }


    @MutationMapping
    @PreAuthorize("hasAnyAuthority('role/update','Ver Roles')")
    public Role updateRole(@Argument String id,@Argument @Valid RoleDto roleDto){
        return roleService.update(id,roleDto);
    }


    @MutationMapping
    @PreAuthorize("hasAnyAuthority('role/delete','Ver Roles')")
    public Role deleteRole(@Argument String id){
        return roleService.delete(id);
    }
}
