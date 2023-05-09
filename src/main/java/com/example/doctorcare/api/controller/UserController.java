package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.request.ChangePassword;
import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import com.example.doctorcare.api.domain.dto.response.MessageResponse;
import com.example.doctorcare.api.domain.dto.response.UserInformation;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.event.OnSendAppointmentInfoEvent;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MANAGER','ROLE_DOCTOR')")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

/*    @GetMapping("/userProfile")
    public ResponseEntity<?> getUserProfile(){
        UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
        return new ResponseEntity<>(new UserInformation(user.getId(),user.getBirthday().toString(),user.getEmail(),user.getAddress(),user.getFullName(),user.getGender(),user.getPhone(),user.getNationality()), HttpStatus.OK);
    }*/

    @GetMapping("/userProfile")
    public ResponseEntity<?> getUserProfile() {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            UserInformation userInformation = new UserInformation();
            BeanUtils.copyProperties(user, userInformation, "birthday");
            userInformation.setGender(user.getGender().toString());
            userInformation.setBirthday(user.getBirthday().toString());
            return new ResponseEntity<>(userInformation, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/testSendMail")
    public ResponseEntity<?> testSendEmail(HttpSession session) {
        try {
            String username = SecurityUtils.getUsername().trim();
            session.setAttribute("username", username);
            eventPublisher.publishEvent(new OnSendAppointmentInfoEvent(this, username,new AppointmentInfoForUser()));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/editProfileUser")
    public ResponseEntity<?> doEditProfileUser(@RequestBody UserInformation userInformation) {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            BeanUtils.copyProperties(userInformation, user, "birthday", "id");
            user.setBirthday(LocalDate.parse(userInformation.getBirthday()));
           /* user.setGender(userInformation.getGender());
            user.setFullName(userInformation.getFullName());
            user.setEmail(userInformation.getEmail());
            user.setNationality(userInformation.getNationality());
            user.setPhone(userInformation.getPhone());
            user.setAddress(userInformation.getAddress());*/
            userDetailsService.save(user);
            return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new MessageResponse("Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> doChangePassword(@RequestBody ChangePassword changePassword) {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            if (SecurityUtils.checkOldPassword(user, changePassword.getOldPassword())) {
                user.setPassword(SecurityUtils.encrytePassword(changePassword.getNewPassword()));
                userDetailsService.save(user);
                return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
            } else return new ResponseEntity<>(new MessageResponse("Wrong password"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new MessageResponse("Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
