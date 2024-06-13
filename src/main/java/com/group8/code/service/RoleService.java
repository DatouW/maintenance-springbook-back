package com.group8.code.service;

import com.group8.code.domain.Role;
import com.group8.code.dto.RoleDto;
import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findById(String id);

    Role findByName(String name);

    Role create(RoleDto roleDto);

    Role update(String id, RoleDto roleDto);

    Role delete(String id);
}