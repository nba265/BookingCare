package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.Mapper.HospitalClinicMapper;
import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.dto.request.AddService;
import com.example.doctorcare.api.domain.dto.response.AppoinmentHistory;
import com.example.doctorcare.api.domain.dto.response.AppointmentCustomer;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.ServiceEnum;
import com.example.doctorcare.api.service.AppointmentsService;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.ServicesService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/manager")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class ManagerController {

    @Autowired
    HospitalClinicMapper hospitalCilinicMapper;

    @Autowired
    ServicesService servicesService;
    @Autowired
    HospitalClinicService hospitalClinicService;
    @Autowired
    ServiceMapper serviceMapper;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AppointmentsService appointmentsService;
    @Autowired
    AppointmentMapper appointmentMapper;

    PaginationAndSortUtil paginationAndSortUtil = new PaginationAndSortUtil();


    @GetMapping("/appointmentHistory")
    public ResponseEntity<?> appointmentHistory(
            @RequestParam(required = false) String bookName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LocalDate before,
            @RequestParam(required = false) LocalDate after
    ) {
        try {
            HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername());
            List<AppoinmentHistory> appointmentHistories = new ArrayList<>();
            List<AppointmentsEntity> appointmentsEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            Page<AppointmentsEntity> pageTuts = appointmentsService.findByHospitalCustomerCreateDate(hospitalClinicEntity.getId(), pagingSort, bookName, before, after);
            appointmentsEntities = pageTuts.getContent();
            if (appointmentsEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            appointmentsEntities.forEach(appointments -> {
                AppoinmentHistory appointmentHistory = new AppoinmentHistory();
                appointmentHistory.setId(appointments.getId());
                appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
                appointmentHistory.setAppointmentCode(appointments.getAppointmentCode());
                appointmentHistories.add(appointmentHistory);
            });
            Map<String, Object> response = new HashMap<>();
            response.put("appointmentList", appointmentHistories);
            response.put("currentPage", pageTuts.getNumber() + 1);
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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

    @PutMapping("/editStatusAppointment")
    public ResponseEntity<?> editStatusAppointment(@RequestParam("status") String status, @RequestParam("id") Long id) {
        try {
            AppointmentsEntity appointmentsEntity = appointmentsService.findById(id);
            appointmentsEntity.setStatus(AppointmentStatus.valueOf(status));
            appointmentsService.save(appointmentsEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editServiceAppointment")
    public ResponseEntity<?> editServiceAppointment(@RequestParam("idAppointment") Long idAppointment, @RequestParam("idService") Long idService) {
        try {
            AppointmentsEntity appointmentsEntity = appointmentsService.findById(idAppointment);
            ServicesEntity servicesEntity = servicesService.findById(idService);
            appointmentsEntity.setServices(servicesEntity);
            appointmentsService.save(appointmentsEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createEditService")
    public ResponseEntity<?> createTimeService(@RequestBody AddService service) {
        try {
            HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername());
            Services services = new Services();
            if (service.getId() == null) {
                services.setId(0L);
            } else services.setId(service.getId());
            services.setName(service.getName());
            services.setPrice(service.getPrice());
            services.setDescription(service.getDescription());
            services.setHospitalCilinic(hospitalClinicEntity);
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

    @GetMapping("/getAllService")
    public ResponseEntity<?> getAllService() {
        try {
            return new ResponseEntity<>(servicesService.findAllByHospitalCilinic_Id(hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername()).getId()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            return new ResponseEntity<>(hospitalClinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getServiceById")
    public ResponseEntity<?> displayEditSerivice(Long id) {
        try {
            return new ResponseEntity<>(serviceMapper.convertToDto(servicesService.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteServiceById")
    public ResponseEntity<?> deleteService(Long id) {
        try {
            return new ResponseEntity<>(serviceMapper.convertToDto(servicesService.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editStatus")
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
            services.setHospitalCilinic(hospitalClinicService.findById(addService.getId()));
            servicesService.save(services);
        } catch (Exception e
        ) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
