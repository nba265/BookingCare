package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.service.HospitalCilinicService;
import com.example.doctorcare.api.service.ServicesService;
import com.example.doctorcare.api.service.SpecialistService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    HospitalCilinicService hospitalCilinicService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    ServicesService servicesService;

    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital(){
        try{
            return new ResponseEntity<>(hospitalCilinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctorByHospitalCilinic(@RequestParam("hos_id") Long hosId){
        try {
            return new ResponseEntity<>(userDetailsService.findDoctorByHospitalCilinic(hosId),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchHospital")
    public ResponseEntity<?> searchHospital(@RequestParam("keyword")String keyword){
        try{
            return new ResponseEntity<>(hospitalCilinicService.findByKeywords(keyword),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/specialist")
    public ResponseEntity<?> allSpecialistInHospital(@RequestParam("id") Long id ){
        try {
            return new ResponseEntity<>(specialistService.findAllByHospitalCilinicId(id),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findDoctor")
    public ResponseEntity<?> findDoctor(@RequestParam("hos_id") Long hosId,@RequestParam("spec_id")Long specId,@RequestParam("gender")String gender, @RequestParam("keyword")String keyword){
        try {
            return new ResponseEntity<>(userDetailsService.findDoctor(hosId,specId,gender,keyword),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listService")
    public ResponseEntity<?> listService(@RequestParam("doctor_id")Long docId){
        try {
            return new ResponseEntity<>(servicesService.findAllByDoctorId(docId),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
