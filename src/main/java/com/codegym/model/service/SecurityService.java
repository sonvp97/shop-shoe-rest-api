package com.codegym.model.service;

public interface SecurityService {
    boolean isAuthenticated();
    boolean isValidToken(String token);
}
