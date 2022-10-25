package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        return (List<UserEntity>) this.userDetailsService.findAll();
    }

    @PostMapping("/createUser")
    public HttpStatus createUser(@RequestBody(required = false) User user){
        try {
            userDetailsService.save(user);
            return HttpStatus.CREATED;
        } catch (Exception e){
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
