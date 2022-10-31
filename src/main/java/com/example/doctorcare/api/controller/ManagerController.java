package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.HospitalCilinicMapper;
import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.dto.request.AddService;
import com.example.doctorcare.api.domain.dto.response.AppoinmentHistory;
import com.example.doctorcare.api.domain.dto.response.AppointmentCustomer;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.enums.ServiceEnum;
import com.example.doctorcare.api.service.AppointmentsService;
import com.example.doctorcare.api.service.HospitalCilinicService;
import com.example.doctorcare.api.service.ServicesService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/manager")
@PreAuthorize("hasRole('manager')")
public class ManagerController {
    @Autowired
    HospitalCilinicMapper hospitalCilinicMapper;
    @Autowired
    ServicesService servicesService;
    @Autowired
    HospitalCilinicService hospitalCilinicService;
    @Autowired
    ServiceMapper serviceMapper;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AppointmentsService appointmentsService;

    @GetMapping("/appointmentHistory")
    public ResponseEntity<?> appointmentHistory() {
        try {
            HospitalCilinicEntity hospitalCilinicEntity = hospitalCilinicService.findByManagerUsername(SecurityUtils.getUsername());
            List<AppoinmentHistory> appointmentHistories = new ArrayList<>();
            Set<AppointmentsEntity> appointmentHistorySet = appointmentsService.findByHospital(hospitalCilinicEntity.getId());
            System.out.println(Arrays.toString(appointmentHistorySet.toArray()));
            appointmentHistorySet.forEach(appointments -> {
                AppoinmentHistory appointmentHistory = new AppoinmentHistory();
                appointmentHistory.setId(appointments.getId());
                appointmentHistory.setDescription(appointmentHistory.getDescription());
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
    public ResponseEntity<?> appointmentInfo(@RequestParam("id") Long id) {
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
            appointmentCustomer.setStatus(appointment.getStatus());
            return new ResponseEntity<>(appointmentCustomer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("edit_status")

    @PostMapping("/create_edit_service")
    public ResponseEntity<?> createTimeService(@RequestBody AddService service) {
        try {
            HospitalCilinicEntity hospitalCilinicEntity = hospitalCilinicService.findByManagerUsername(SecurityUtils.getUsername());
            Services services = new Services();
            if (service.getId() == null) {
                services.setId(0L);
            } else services.setId(service.getId());
            services.setName(service.getName());
            services.setPrice(service.getPrice());
            services.setDescription(service.getDescription());
            services.setHospitalCilinic(hospitalCilinicEntity);
            if (services.getServiceEnum() == null) {
                services.setServiceEnum(ServiceEnum.AVAILABLE);
            }
            servicesService.save(services);
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get_all_service")
    public ResponseEntity<?> getAllService() {
        try {
            return new ResponseEntity<>(servicesService.findAllByHospitalCilinic_Id(hospitalCilinicService.findByManagerUsername(SecurityUtils.getUsername()).getId()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            return new ResponseEntity<>(hospitalCilinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();


            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get_service_by_id")
    public ResponseEntity<?> displayEditSerivice(Long id) {
        try {
            return new ResponseEntity<>(serviceMapper.convertToDto(servicesService.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete_service_by_id")
    public ResponseEntity<?> deleteService(Long id) {
        try {
            return new ResponseEntity<>(serviceMapper.convertToDto(servicesService.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit_status")
    public ResponseEntity<?> changeStatus(@RequestParam("status") String status, @RequestParam("id") Long id) {
        try {
            servicesService.toggleStatus(status, id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addService")

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
