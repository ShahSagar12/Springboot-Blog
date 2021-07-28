package com.rest.treeleaf.users.services;

import com.rest.treeleaf.users.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> save(User user);

    ResponseEntity<?> getUserByEmail(String email);

    String existsByEmail(String email);
}
