package com.codegym.controller;

import com.codegym.model.dto.RoleDto;
import com.codegym.model.dto.UserDto;
import com.codegym.model.service.RoleService;
import com.codegym.model.service.SecurityService;
import com.codegym.payload.request.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RoleDto roleDto,
                                    @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        System.out.println(roleDto.getName());
        System.out.println(roleDto.getDescription());
        roleService.save(roleDto);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        List<RoleDto> roles = roleService.getRoles();
        if (roles.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        RoleDto roleDto = roleService.getRoleById(id);
        if (roleDto == null) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest searchRequest,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        List<RoleDto> roleDtos = null;
        if (searchRequest.getKeyword() != null && !searchRequest.getKeyword().isEmpty()) {
            roleDtos = roleService.getRolesByFullName(searchRequest.getKeyword());
            if (roleDtos.isEmpty()) {
                return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit( @RequestBody RoleDto roleDto,
                                   @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        roleService.save(roleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        roleService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
