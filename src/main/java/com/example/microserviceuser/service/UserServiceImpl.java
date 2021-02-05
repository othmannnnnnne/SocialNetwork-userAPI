package com.example.microserviceuser.service;


import com.example.microserviceuser.model.User;
import com.example.microserviceuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);


    }

    @Override
    public List<String> findUsers(List<Long> idlist){
        return userRepository.findByIdList(idlist);

    }
}
