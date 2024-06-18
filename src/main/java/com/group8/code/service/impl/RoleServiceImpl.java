package com.group8.code.service.impl;

import com.group8.code.domain.Role;
import com.group8.code.dto.Pagination;
import com.group8.code.dto.RoleDto;
import com.group8.code.repository.RoleRepository;
import com.group8.code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Pagination<Role> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Role> all = roleRepository.findAll(pageable);
        return new Pagination<>(all.getTotalPages(),all.getContent());
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Role create(RoleDto roleDto) {
        Role role = Role.builder()
                .name(roleDto.getName())
                .description(roleDto.getDescription())
                .permissions(roleDto.getPermissions())
                .build();
        System.out.println(role);
        return roleRepository.save(role);
    }

    @Override
    public Role update(String id, RoleDto roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not existe"));

        role.setDescription(roleDto.getDescription());
        role.setName(roleDto.getName());
        role.setPermissions(roleDto.getPermissions());
        return roleRepository.save(role);
    }

    @Override
    public Role delete(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id no existe"));
        roleRepository.delete(role);
        return role;
    }
}