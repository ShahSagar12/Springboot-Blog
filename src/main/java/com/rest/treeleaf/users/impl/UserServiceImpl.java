package com.rest.treeleaf.users.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rest.treeleaf.common.models.ResponseModel;
import com.rest.treeleaf.users.entity.User;
import com.rest.treeleaf.users.repository.UserRepository;
import com.rest.treeleaf.users.serializer.UserSerializer;
import com.rest.treeleaf.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
            return ResponseEntity.ok().body(
                    new HashMap<String, String>() {{
                        put("success", "Saved");
                    }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new HashMap<String, String>() {{
                        put("error", "BAD_REQUEST");
                        put("error_message", e.getMessage());

                    }});
        }
    }

    @Override
    public ResponseEntity<?> getUserByEmail(String email) {
        ResponseModel<User, Object> objectResponseModel = new ResponseModel<>();
        Optional<User> user = userRepository.findByEmail(email);
        ObjectMapper mapper = new ObjectMapper();
        if (!user.isPresent())
            return ResponseEntity.badRequest().body(
                    new HashMap<String, String>() {{
                        put("error", "BAD_REQUEST");
                        put("error_message", "No user found");
                    }});
        objectResponseModel.setData(user.get());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializer());
        mapper.registerModule(module);
        try {
            return ResponseEntity.ok().body(
                    mapper.writeValueAsString(objectResponseModel));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    new HashMap<String, String>() {{
                        put("error", "BAD_REQUEST");
                        put("error_message", e.getMessage());
                    }});
        }
    }

    @Override
    public String existsByEmail(String email) {
        ResponseModel<Map<String, Boolean>, Object> responseModel = new ResponseModel<>();
        responseModel.setData(new HashMap<String, Boolean>() {
            {
                put("exists", userRepository.existsByEmail(email));
            }
        });
        try {
            return new ObjectMapper().writeValueAsString(responseModel);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
