package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.request.AddTimeDoctor;
import com.example.doctorcare.api.domain.dto.response.TimeDoctor;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
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
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/doctor")
public class DoctorController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    TimeDoctorService timeDoctorService;
    @Autowired
    UserMapper userMapper;

    @PostMapping("/create_time_doctors")
    public ResponseEntity<?> createTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
            TimeDoctors timeDoctors= new TimeDoctors();
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            timeDoctors.setDoctor(userMapper.convertToDto(user));
            if(timeDoctors1.getId()==null){
                timeDoctors.setId(0L);
            }
            timeDoctors.setTimeStart(LocalTime.parse(timeDoctors1.getTimeStart()));
            timeDoctors.setTimeEnd(LocalTime.parse(timeDoctors1.getTimeEnd()));
            timeDoctors.setDate(LocalDate.parse(timeDoctors1.getCreateDate()));
            User user1=userMapper.convertToDto(user);
            timeDoctors.setDoctor(userMapper.convertToDto(user));
            timeDoctorService.save(timeDoctors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/get_time_doctors")
    public ResponseEntity<?> getTimeDoctor() {
        UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
        List<TimeDoctors> timeDoctorsList = timeDoctorService.findAllByDoctor(user.getId());
        List<TimeDoctor> timeDoctors = new ArrayList<>();
        timeDoctorsList.forEach(timeDoctors1 -> {
            timeDoctors.add(new TimeDoctor(timeDoctors1.getId(), timeDoctors1.getTimeStart().toString(), timeDoctors1.getTimeEnd().toString(), timeDoctors1.getDate().toString()));
        });
        try {
            return new ResponseEntity<>(timeDoctors, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit_time_doctors")
    public ResponseEntity<?> editTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
            TimeDoctors timeDoctors= new TimeDoctors();
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();

            timeDoctors.setDoctor(userMapper.convertToDto(user));
            timeDoctors.setTimeStart(LocalTime.parse(timeDoctors1.getTimeStart()));
            timeDoctors.setTimeEnd(LocalTime.parse(timeDoctors1.getTimeEnd()));
            timeDoctors.setDate(LocalDate.parse(timeDoctors1.getCreateDate()));
            timeDoctorService.save(timeDoctors);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/display_edit_time_doctors")
    public ResponseEntity<?> editTimeDoctor(@RequestParam("id") Long id) {
        TimeDoctors timeDoctors = timeDoctorService.findById(id);
        try {
            return new ResponseEntity<>(new TimeDoctor(timeDoctors.getId(), timeDoctors.getTimeStart().toString(), timeDoctors.getTimeEnd().toString(), timeDoctors.getDate().toString()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/delete_time_doctors")
    public ResponseEntity<?> deleteTimeDoctor(@RequestParam("id") Long id) {
        try {
            timeDoctorService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
