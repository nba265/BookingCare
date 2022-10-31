package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.request.MakeAppointment;
import com.example.doctorcare.api.domain.dto.response.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/client")
@PreAuthorize("hasRole('ROLE_USER')")
public class ClientController {

    @Autowired
    HospitalClinicService hospitalClinicService;

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

/*    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            List<HospitalClinicInfoResponse> responses = new ArrayList<>();
            hospitalClinicService.hospitalCilinicList().forEach(hospitalCilinic -> {
                responses.add(new HospitalClinicInfoResponse(hospitalCilinic.getId(),hospitalCilinic.getName()));
            });
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

/*    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctorByHospitalCilinic(@RequestParam("hos_id") Long hosId) {
        try {
            List<DoctorInfoResponse> doctorInfoResponses = new ArrayList<>();
            userDetailsService.findDoctorByHospitalCilinic(hosId).forEach(user -> {
                doctorInfoResponses.add(new DoctorInfoResponse(user.getId(),user.getFullName(),user.getGender(),user.getDegree(),user.getNationality(),user.getExperience(),user.getSpecialist()));
            });
            return new ResponseEntity<>(doctorInfoResponses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/listHospital")
    public ResponseEntity<?> searchHospital(@RequestParam(name = "keyword", required = false) String keyword) {
        try {
            List<HospitalClinicInfoResponse> responses = new ArrayList<>();
            hospitalClinicService.findByKeywords(keyword).forEach(hospitalCilinic -> {
                responses.add(new HospitalClinicInfoResponse(hospitalCilinic.getId(), hospitalCilinic.getName(),hospitalCilinic.getAddress(),hospitalCilinic.getPhone()));
            });
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    @GetMapping("/specialist")
    public ResponseEntity<?> allSpecialistInHospital(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(specialistService.findAllByHospitalCilinicId(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctor(@RequestParam(name = "hos_id", required = true) Long hosId, @RequestParam(name = "spec_id", required = false) Long specId, @RequestParam(name = "gender", required = false) String gender, @RequestParam(name = "keyword", required = false) String keyword) {
        try {
            List<DoctorInfoResponse> doctorInfoResponses = new ArrayList<>();
            userDetailsService.findDoctor(hosId, specId, gender, keyword).forEach(user -> {
                doctorInfoResponses.add(new DoctorInfoResponse(user.getId(), user.getFullName(), user.getGender(), user.getDegree(), user.getNationality(), user.getExperience(), user.getSpecialist().getName()));
            });
            return new ResponseEntity<>(doctorInfoResponses, HttpStatus.OK);
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

    @GetMapping("/bookingServiceDatetime")
    public ResponseEntity<?> bookingServiceAndDatetime(@RequestParam("doctor_id") Long doctorId) {
        try {
            DoctorSearchInfo doctorSearchInfo = new DoctorSearchInfo();
            User doctor = userDetailsService.findDoctorById(doctorId);
            doctorSearchInfo.setDoctorName(doctor.getFullName());
            doctorSearchInfo.setServicesList(servicesService.findAllByHospitalCilinic_Id(doctor.getHospitalClinicDoctor().getId()));
            doctorSearchInfo.setHospName(doctor.getHospitalClinicDoctor().getName());
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

    @PostMapping("/createAppointment")
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

    @GetMapping("/appointmentHistory")
    public ResponseEntity<?> appointmentHistory() {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            List<AppoinmentHistory> appointmentHistories = new ArrayList<>();
            Set<AppointmentsEntity> appointmentHistorySet = user.getAppointmentsEntities();
            appointmentHistorySet.forEach(appointments -> {
                AppoinmentHistory appointmentHistory = new AppoinmentHistory();
                appointmentHistory.setId(appointments.getId());
                appointmentHistory.setDescription(appointmentHistory.getDescription());
                appointmentHistory.setHospitalName(hospitalClinicService.findByAppointment_Id(appointments.getId()).getName());
                appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
                appointmentHistories.add(appointmentHistory);
            });
            return new ResponseEntity<>(appointmentHistories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/appointmentInfo")
    public ResponseEntity<?> appointmentInfo(@RequestParam("appointmentId") Long id) {
        try {
            AppointmentCustomer appointmentCustomer = new AppointmentCustomer();
            AppointmentsEntity appointment = appointmentsService.findById(id);
            appointmentCustomer.setDoctorName(appointment.getUser().getFullName());
            appointmentCustomer.setPhoneDoctor(appointment.getUser().getPhone());
            appointmentCustomer.setGenderDoctor(appointment.getUser().getGender().toString());
            appointmentCustomer.setGenderCustomer(appointment.getCustomers().getGender().toString());
            appointmentCustomer.setBirthday(appointment.getCustomers().getBirthday().toString());
            appointmentCustomer.setNamePatient(appointment.getCustomers().getNamePatient());
            appointmentCustomer.setPhonePatient(appointment.getCustomers().getPhonePatient());
            return new ResponseEntity<>(appointmentCustomer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
