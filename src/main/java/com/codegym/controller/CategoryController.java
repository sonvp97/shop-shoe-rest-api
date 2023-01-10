package com.codegym.controller;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.dto.UserDto;
import com.codegym.model.service.CategoryService;
import com.codegym.model.service.SecurityService;
import com.codegym.payload.request.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SecurityService securityService;
    @GetMapping({"/all"})
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        Iterable<CategoryDto> categoryDtos = categoryService.findAll();

        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto,
                                    @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        categoryService.save(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        CategoryDto categoryDto = categoryService.findById(Long.valueOf(id)).get();
        if (categoryDto == null) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

//    @PostMapping("/search")
//    public ResponseEntity<?> search(@RequestBody SearchRequest searchRequest,
//                                    @RequestHeader("Authorization") final String authToken) {
//        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
//            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
//        }
//        List<UserDto> userDtos = null;
//        if (searchRequest.getKeyword() != null && !searchRequest.getKeyword().isEmpty()) {
//            userDtos = userService.getUsersByFullName(searchRequest.getKeyword());
//            if (userDtos.isEmpty()) {
//                return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
//            }
//        }
//        return new ResponseEntity<>(userDtos, HttpStatus.OK);
//    }
    @PutMapping("/edit")
    public ResponseEntity<?> edit( @RequestBody CategoryDto categoryDto,
                                   @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        categoryService.save(categoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        categoryService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
