package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.request.MakeAppointment;
import com.example.doctorcare.api.domain.dto.response.DoctorSearchInfo;
import com.example.doctorcare.api.domain.dto.response.TimeDoctor;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.CustomersEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.*;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    TimeDoctorsMapper timeDoctorsMapper;

    @Autowired
    AppointmentsService appointmentsService;

    @Autowired
    AppointmentMapper appointmentMapper;

    @PostMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            return new ResponseEntity<>(hospitalCilinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctorByHospitalCilinic(@RequestParam("hos_id") Long hosId) {
        try {
            return new ResponseEntity<>(userDetailsService.findDoctorByHospitalCilinic(hosId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchHospital")
    public ResponseEntity<?> searchHospital(@RequestParam("keyword") String keyword) {
        try {
            return new ResponseEntity<>(hospitalCilinicService.findByKeywords(keyword), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/specialist")
    public ResponseEntity<?> allSpecialistInHospital(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(specialistService.findAllByHospitalCilinicId(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findDoctor")
    public ResponseEntity<?> findDoctor(@RequestParam("hos_id") Long hosId, @RequestParam("spec_id") Long specId, @RequestParam("gender") String gender, @RequestParam("keyword") String keyword) {
        try {
            return new ResponseEntity<>(userDetailsService.findDoctor(hosId, specId, gender, keyword), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> bookingServiceAndDatetime(@RequestParam("doctor_id") Long doctorId) {
        try {
            DoctorSearchInfo doctorSearchInfo = new DoctorSearchInfo();
            User doctor = userDetailsService.findDoctorById(doctorId);
            doctorSearchInfo.setDoctorName(doctor.getFullName());
            doctorSearchInfo.setServicesList(servicesService.findAllByHospitalCilinic_Id(doctor.getHospitalCilinicDoctor().getId()));
            doctorSearchInfo.setHospName(doctor.getHospitalCilinicDoctor().getName());
            doctorSearchInfo.setSpecialist(doctor.getSpecialist().getName());
            doctorSearchInfo.setDoctorId(doctorId);
            List<TimeDoctors> timeDoctorsList = timeDoctorService.findFreeTimeByDoctorId(doctorId);
            timeDoctorsList.forEach(timeDoctors -> {
                TimeDoctor timeDoctor = new TimeDoctor();
                timeDoctor.setTimeEnd(timeDoctors.getTimeEnd().toString());
                timeDoctor.setTimeStart(timeDoctors.getTimeStart().toString());
                timeDoctor.setId(timeDoctors.getId());
                timeDoctor.setDate(timeDoctors.getDate().toString());
                doctorSearchInfo.getTimeDoctors().add(timeDoctor);
            });
            return new ResponseEntity<>(doctorSearchInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/make_appointment")
    public ResponseEntity<?> makeAppointment(@RequestBody MakeAppointment makeAppointment) {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            AppointmentsEntity appointments = new AppointmentsEntity();
            appointments.setCreateDate(LocalDateTime.now());
            appointments.setTimeDoctors(timeDoctorService.findByIdEntity(makeAppointment.getTimeDoctorId()));
            appointments.getTimeDoctors().setAppointments(appointments);
            appointments.setServices(servicesService.findById(makeAppointment.getServId()));
            CustomersEntity customers = new CustomersEntity();
            customers.setEmail(user.getEmail());
            customers.setNameBooking(user.getFullName());
            customers.setPhoneBooking(user.getPhone());
            customers.setGender(makeAppointment.getGender());
            customers.setBirthday(LocalDate.parse(makeAppointment.getBirthday()));
            customers.setPhonePatient(makeAppointment.getPhone());
            customers.setNamePatient(makeAppointment.getFullName());
            appointments.setCustomers(customers);
            appointments.setDescription(makeAppointment.getDescription());
            appointmentsService.save(appointments);
            return new ResponseEntity<>(appointmentMapper.convertToDto(appointments), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
