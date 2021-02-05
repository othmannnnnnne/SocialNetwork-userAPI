package com.example.microserviceuser.service;

import com.example.microserviceuser.model.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findByUsername(String username);

    List<String> findUsers(List<Long> idlist);
}
