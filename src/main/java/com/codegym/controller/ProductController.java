package com.codegym.controller;

import com.codegym.model.dto.ProductDto;
import com.codegym.model.dto.UserDto;
import com.codegym.model.entity.Product;
import com.codegym.model.repository.ProductRepository;
import com.codegym.model.service.ProductService;
import com.codegym.model.service.SecurityService;
import com.codegym.payload.request.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping({"/all"})
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        Iterable<ProductDto> products = productService.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product,
                                    @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        ProductDto productDto = productService.findById(Long.valueOf(id)).get();
        if (productDto == null) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
//
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
    public ResponseEntity<?> edit( @RequestBody Product product,
                                   @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        productService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
