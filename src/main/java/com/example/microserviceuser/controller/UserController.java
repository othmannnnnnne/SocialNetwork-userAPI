package com.example.microserviceuser.controller;


import com.example.microserviceuser.model.Role;
import com.example.microserviceuser.model.User;
import com.example.microserviceuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Value("&(spring.application.name)")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service port number : " + env.getProperty("local.server.port");

    }

    @PostMapping("/service/instances")
    public ResponseEntity<?> getInstances(){
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId),HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(),HttpStatus.OK);
    }


    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if(userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(Principal principal){
        if(principal==null || principal.getName()==null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));


    }

    @PostMapping("/service/names")
    public ResponseEntity<?> getNameOfUsers(@RequestBody List<Long> idList){
        return ResponseEntity.ok(userService.findUsers(idList));


    }

    @GetMapping("/service/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Working");
    }
}
