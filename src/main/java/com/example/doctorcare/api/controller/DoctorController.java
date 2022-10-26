package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.TimeDoctorService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;


@RestController
@RequestMapping("api/doctor")
public class DoctorController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    TimeDoctorService timeDoctorService;

    @PostMapping
    public ResponseEntity<?> createTimeDoctor(@RequestParam("timeStart") LocalTime timeStart, @RequestParam("timeEnd") LocalTime timeEnd) {
        try {
            TimeDoctors timeDoctors = new TimeDoctors();
            UserEntity user = userDetailsService.findByEmail(SecurityUtils.getUsername());
            timeDoctors.setDoctor(user);
            timeDoctors.setTimeEnd(timeEnd);
            timeDoctors.setTimeStart(timeStart);
            timeDoctors.setDate(LocalDate.now());
            timeDoctorService.save(timeDoctors);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getTimeDoctor(){
        UserEntity user = userDetailsService.findByEmail(SecurityUtils.getUsername());
        try {
            return new ResponseEntity<>(timeDoctorService.findAllByDoctor(user.getId()),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
