package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.HospitalClinic;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.SpecialistService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/hospitalcilinic")
public class HospitalClinicController {

    @Autowired
    HospitalClinicService hospitalClinicService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital(){
        try{
            return new ResponseEntity<>(hospitalClinicService.hospitalCilinicList(),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctorByHospitalCilinic(@RequestParam("hos_id") Long hosId ){
        try {
            return new ResponseEntity<>(userDetailsService.findDoctorByHospitalCilinic(hosId),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchHospital")
    public ResponseEntity<?> searchHospital(@RequestParam("keyword")String keyword,@RequestParam(value = "districtCode",required = false) String districtCode){
        try{
            return new ResponseEntity<>(hospitalClinicService.findByKeywordsOrDistrictCode(keyword,districtCode),HttpStatus.OK);
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

    @PostMapping("/createHospitalClinic")
    public ResponseEntity<?> createHospitalCilinic(@RequestParam("name")String name ){
            HospitalClinic hospitalClinic = new HospitalClinic();
            hospitalClinic.setName(name);
            hospitalClinicService.save(hospitalClinic);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
