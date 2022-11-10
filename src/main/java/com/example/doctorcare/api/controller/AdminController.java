package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.request.AddHospital;
import com.example.doctorcare.api.domain.dto.response.MessageResponse;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private HospitalClinicService hospitalClinicService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;


    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            return new ResponseEntity<>(hospitalClinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkUser")
    public ResponseEntity<?> getUsers(@RequestParam("username") String username) {
        try {

            return new ResponseEntity<>(userDetailsService.checkUser(username), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addHospital")
    public ResponseEntity<?> addHospital(@RequestBody AddHospital hospitalCilinic) {
        try {
            if (userDetailsService.checkUser(hospitalCilinic.getUsername())) {
                UserEntity user = userDetailsService.findByUsername(hospitalCilinic.getUsername()).get();
                HospitalClinicEntity hospitalClinicEntity;
                if (hospitalCilinic.getId() == null) {
                    hospitalClinicEntity = new HospitalClinicEntity();
                } else {
                    hospitalClinicEntity = hospitalClinicService.findById(hospitalCilinic.getId());
                }
                hospitalClinicEntity.setAddress(hospitalCilinic.getAddress());
                hospitalClinicEntity.setPhone(hospitalCilinic.getPhone());
                hospitalClinicEntity.setManager(user);
                hospitalClinicEntity.setName(hospitalCilinic.getName());
                hospitalClinicService.save(hospitalClinicEntity);
                return new ResponseEntity<>(new MessageResponse("success"), HttpStatus.OK);
            } else return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong Manager Username"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
