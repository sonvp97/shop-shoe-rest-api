package com.codegym.model.service;

import com.codegym.model.dto.RoleDto;
import com.codegym.model.entity.Role;
import com.codegym.model.entity.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDto> getRoles();
    RoleDto getRoleById(Integer userId);
    List<RoleDto> getRolesByFullName(String fullName);
    Iterable<RoleDto> findAll();
    Optional<RoleDto> findById(Long id);
    void save(RoleDto roleDto);
    void remove(Long id);
    Optional<Role> findByName(RoleName name);
}
