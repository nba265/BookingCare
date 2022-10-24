package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.service.HospitalCilinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/hospitalcilinic")
public class HospitalCilinicController {

    @Autowired
    HospitalCilinicService hospitalCilinicService;

    @PostMapping("/addhc")
    public ResponseEntity<?> addHospitalCilinic(@Valid @RequestBody HospitalCilinicEntity hospitalCilinic){
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalCilinicService.save(hospitalCilinic));
    }
}
