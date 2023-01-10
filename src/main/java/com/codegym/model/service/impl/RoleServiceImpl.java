package com.codegym.model.service.impl;

import com.codegym.model.dto.RoleDto;
import com.codegym.model.entity.Role;
import com.codegym.model.entity.RoleName;
import com.codegym.model.repository.RoleRepository;
import com.codegym.model.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@ComponentScan(basePackageClasses = ModelMapper.class)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(user -> modelMapper.map(user,RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(Integer roleId) {
        Role role = roleRepository.findById(Long.valueOf(roleId)).orElse(null);
        return modelMapper.map(role,RoleDto.class);
    }

    @Override
    public List<RoleDto> getRolesByFullName(String fullName) {
        String likeFullName = "%" + fullName + "%";
        List<Role> roles = roleRepository.findByFullName(likeFullName);
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<RoleDto> findAll() {
        Iterable<Role> entities = roleRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                            .map(entity -> modelMapper.map(entity, RoleDto.class))
                            .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        Role entity = roleRepository.findById(id).orElse(null);
        return Optional.ofNullable(modelMapper.map(entity, RoleDto.class));
    }

    @Override
    public void save(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);

        roleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }



    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
