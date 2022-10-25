package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.service.HospitalCilinicService;
import com.example.doctorcare.api.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/hospitalcilinic")
public class HospitalCilinicController {

    @Autowired
    HospitalCilinicService hospitalCilinicService;

    @Autowired
    SpecialistService specialistService;

    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital(){
        try{
            return new ResponseEntity<>(hospitalCilinicService.hospitalCilinicList(),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctorByHospitalCilinicAndSpecialist(@RequestParam("hos_id") Long hosId,@RequestParam("spec_id") Long specId ){
        try {
            return new ResponseEntity<>(hospitalCilinicService.findDoctorByHospitalCilinicAndSpecialist(hosId,specId),HttpStatus.OK);
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

    @PostMapping("/createHospitalCilinic")
    public ResponseEntity<?> createHospitalCilinic(@RequestParam("name")String name ){
            HospitalCilinic hospitalCilinic = new HospitalCilinic();
            hospitalCilinic.setName(name);
            hospitalCilinicService.save(hospitalCilinic);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
