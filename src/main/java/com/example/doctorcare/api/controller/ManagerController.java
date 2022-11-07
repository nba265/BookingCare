package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.Mapper.HospitalClinicMapper;
import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.request.AddService;
import com.example.doctorcare.api.domain.dto.response.AppoinmentHistory;
import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import com.example.doctorcare.api.domain.dto.response.MessageResponse;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
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
import java.util.*;


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
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) LocalDate before,
            @RequestParam(required = false) LocalDate after
    ) {
        try {
            HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername());
            List<AppoinmentHistory> appointmentHistories = new ArrayList<>();
            List<AppointmentsEntity> appointmentsEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            Page<AppointmentsEntity> pageTuts = appointmentsService.findByHospitalCustomerCreateDate(hospitalClinicEntity.getId(), pagingSort, before, after);
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
            AppointmentInfoForUser appointmentInfoForUser = new AppointmentInfoForUser();
            AppointmentsEntity appointment = appointmentsService.findById(id);
            appointmentInfoForUser.setDoctorName(appointment.getUser().getFullName());
            appointmentInfoForUser.setPhoneDoctor(appointment.getUser().getPhone());
            appointmentInfoForUser.setGenderDoctor(appointment.getUser().getGender().toString());
            appointmentInfoForUser.setGenderCustomer(appointment.getCustomers().getGender().toString());
            appointmentInfoForUser.setBirthday(appointment.getCustomers().getBirthday().toString());
            appointmentInfoForUser.setNamePatient(appointment.getCustomers().getNamePatient());
            appointmentInfoForUser.setPhonePatient(appointment.getCustomers().getPhonePatient());
            appointmentInfoForUser.setStatus(appointment.getStatus());
            appointmentInfoForUser.setService(appointment.getServices().getName());
            appointmentInfoForUser.setPrice(appointment.getServices().getPrice().toString());
            return new ResponseEntity<>(appointmentInfoForUser, HttpStatus.OK);
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
    public ResponseEntity<?> createOrEditService(@RequestBody AddService service) {
        try {
            UserEntity manager = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            ServicesEntity servicesEntity = new ServicesEntity();
            Long hospitalId = null;
            if (service.getId() != null) {
                servicesEntity = servicesService.findById(service.getId());
                hospitalId = servicesEntity.getHospitalCilinic().getId();
            }
            HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByManagerUsername(manager.getUsername());
            if (hospitalId == null || Objects.equals(hospitalClinicEntity.getId(), hospitalId)) {
/*                if (service.getId() == null) {
                    servicesEntity.setId(0L);
                } else servicesEntity.setId(service.getId());*/
                servicesEntity.setName(service.getName());
                servicesEntity.setPrice(service.getPrice());
                servicesEntity.setDescription(service.getDescription());
                servicesEntity.setHospitalCilinic(hospitalClinicEntity);
                if (servicesEntity.getServiceEnum() == null) {
                    servicesEntity.setServiceEnum(ServiceEnum.AVAILABLE);
                }
                servicesService.saveEntity(servicesEntity);
                return new ResponseEntity<>("Success!", HttpStatus.OK);
            } else return new ResponseEntity<>(new MessageResponse("Not Found!"), HttpStatus.NOT_FOUND);
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


/*    @PostMapping("/addService")

    public ResponseEntity<?> addService(@RequestBody AddService addService) {
        try {
            ServicesEntity services = new ServicesEntity();
            services.setName(addService.getName());
            services.setDescription(addService.getDescription());
            services.setPrice(addService.getPrice());
            services.setHospitalCilinic(hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername()));
            services.setServiceEnum(ServiceEnum.AVAILABLE);
            servicesService.saveEntity(services);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new MessageResponse("Success!"), HttpStatus.CREATED);
    }*/
}
