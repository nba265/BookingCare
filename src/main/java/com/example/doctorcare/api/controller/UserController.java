package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.response.UserInformation;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
@PreAuthorize("hasRole('ROLE_USER,ROLE_ADMIN,ROLE_MANAGER,ROLE_DOCTOR')")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/userProfile")
    public ResponseEntity<?> getUserProfile(){
        UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
        return new ResponseEntity<>(new UserInformation(user.getId(),user.getBirthday().toString(),user.getEmail(),user.getAddress(),user.getFullName(),user.getGender(),user.getPhone(),user.getNationality()), HttpStatus.OK);
    }

    @PostMapping("/editProfileUser")
    public ResponseEntity<?> doEditProfileUser(@Valid @RequestBody UserInformation userInformation){
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            user.setBirthday(LocalDate.parse(userInformation.getBirthday()));
            user.setGender(userInformation.getGender());
            user.setFullName(userInformation.getFullName());
            user.setEmail(userInformation.getEmail());
            user.setNationality(userInformation.getNationality());
            user.setPhone(userInformation.getPhone());
            user.setAddress(userInformation.getAddress());
            userDetailsService.save(user);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
