package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.dto.request.AddService;
import com.example.doctorcare.api.service.HospitalCilinicService;
import com.example.doctorcare.api.service.ServicesService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/manager")
public class ManagerController {
    @Autowired
    HospitalCilinicService hospitalCilinicService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    ServicesService servicesService;

    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            return new ResponseEntity<>(hospitalCilinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> addService(@RequestBody AddService addService) {
        try {
            Services services = new Services();
            services.setName(addService.getName());
            services.setDescription(addService.getName());
            services.setPrice(addService.getPrice());
            services.setHospitalCilinic(hospitalCilinicService.findById(addService.getId()));
            servicesService.save(services);
        } catch (Exception e
        ) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
