package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("users")

    public List<UserEntity> getUsers(){
        return (List<UserEntity>) this.userDetailsService.findAll();
    }
}
