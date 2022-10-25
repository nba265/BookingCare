package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.service.HospitalCilinicService;
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

  /*  @PostMapping("/addhc")
    public ResponseEntity<?> addHospitalCilinic(@Valid @RequestBody HospitalCilinicEntity hospitalCilinic){
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalCilinicService.save(hospitalCilinic));
    }*/
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
    public ResponseEntity<?> getDoctorByHospitalCilinicId(@RequestParam("id") Long id){
        try {
            return new ResponseEntity<>(hospitalCilinicService.findAllDoctorInHospitalCilinic(id),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
