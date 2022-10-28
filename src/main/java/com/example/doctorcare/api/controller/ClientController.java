package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.response.DoctorSearchInfo;
import com.example.doctorcare.api.domain.dto.response.ListTimeDoctor;
import com.example.doctorcare.api.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/client")
@PreAuthorize("hasRole('ROLE_USER')")
public class ClientController {

    @Autowired
    HospitalCilinicService hospitalCilinicService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    ServicesService servicesService;

    @Autowired
    TimeDoctorService timeDoctorService;

    @PostMapping("/listHospital")
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

/*    @GetMapping("/listService")
    public ResponseEntity<?> listService(@RequestParam("doctor_id")Long docId){
        try {
            return new ResponseEntity<>(servicesService.findAllByDoctorId(docId),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/booking_service_datetime")
    public ResponseEntity<?> bookingServiceAndDatetime(@RequestParam("doctor_id")Long doctorId){
        try {
            DoctorSearchInfo doctorSearchInfo = new DoctorSearchInfo();
            User doctor = userDetailsService.findDoctorById(doctorId);
            doctorSearchInfo.setDoctorName(doctor.getFullName());
            doctorSearchInfo.setServicesList(servicesService.findAllByHospitalCilinic_Id(doctor.getHospitalCilinicDoctor().getId()));
            doctorSearchInfo.setHospName(doctor.getHospitalCilinicDoctor().getName());
            doctorSearchInfo.setSpecialist(doctor.getSpecialist().getName());
            List<TimeDoctors> timeDoctorsList = timeDoctorService.findFreeTimeByDoctorId(doctorId);
            timeDoctorsList.forEach(timeDoctors -> {
                ListTimeDoctor listTimeDoctor = new ListTimeDoctor();
                listTimeDoctor.setTimeEnd(timeDoctors.getTimeEnd().toString());
                listTimeDoctor.setTimeStart(timeDoctors.getTimeStart().toString());
                listTimeDoctor.setId(timeDoctors.getId());
                listTimeDoctor.setDate(timeDoctors.getDate().toString());
                doctorSearchInfo.getTimeDoctors().add(listTimeDoctor);
            });
            return new ResponseEntity<>(doctorSearchInfo,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
