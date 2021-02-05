package com.example.microserviceuser.model;


import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name= "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="name")
    private String name;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Enumerated(value= EnumType.STRING)
    @Column(name="role")
    private Role role;

}
