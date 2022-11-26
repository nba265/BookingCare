package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.Mapper.HospitalClinicMapper;
import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.dto.request.*;
import com.example.doctorcare.api.domain.dto.response.*;
import com.example.doctorcare.api.domain.entity.*;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.ServiceEnum;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.service.*;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    SpecialistService specialistService;

    @Autowired
    UserRepository userRepository;


    PaginationAndSortUtil paginationAndSortUtil = new PaginationAndSortUtil();


    @GetMapping("/appointmentHistory")
    public ResponseEntity<?> appointmentHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size,
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
                appointmentHistory.setHospitalName(hospitalClinicEntity.getName());
                appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
                appointmentHistory.setStatus(appointments.getStatus().toString());
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
    public ResponseEntity<?> appointmentInfo(@RequestParam("appointmentId") Long id) {
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
            ServicesEntity servicesEntity = servicesService.findById(idService).get();
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
                servicesEntity = servicesService.findById(service.getId()).get();
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

    @GetMapping("/checkUsername")
    public ResponseEntity<?> checkExistsUsername(@RequestParam(value = "username", required = false) String username) {
        try {
            if (username != null) {
                return new ResponseEntity<>(!userDetailsService.checkExistsUsername(username), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkExistsEmail(@RequestParam(value = "email", required = false) String email) {
        try {
            if (email != null) {
                return new ResponseEntity<>(!userDetailsService.checkExistsEmail(email), HttpStatus.OK);
            } else {

                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }


    @PostMapping("/createEditSpecialist")
    public ResponseEntity<?> createOrEditSpecialist(@RequestBody AddSpecialist specialist) {
        try {
            UserEntity manager = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            SpecialistEntity specialistEntity = new SpecialistEntity();
            Long hospitalId = null;
            if (specialist.getId() != null) {
                specialistEntity = specialistService.findById(specialist.getId()).get();
                hospitalId = specialistEntity.getHospitalCilinic().getId();
            }
            HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByManagerUsername(manager.getUsername());
            if (hospitalId == null || Objects.equals(hospitalClinicEntity.getId(), hospitalId)) {
                specialistEntity.setName(specialist.getName());
                specialistEntity.setHospitalCilinic(hospitalClinicEntity);
                specialistService.saveEntity(specialistEntity);
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
            return new ResponseEntity<>(servicesService.findAllByHospitalCilinic_IdManager(hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername()).getId()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllSpecialist")
    public ResponseEntity<?> getAllSpecialist() {
        try {
            List<SpecialistEntity> specialist = (List<SpecialistEntity>) specialistService.findByHospitalCilinic_Id(hospitalClinicService.findByManagerUsername(SecurityUtils.getUsername()).getId());
            List<SpecialistResponse> specialistResponses = new ArrayList<>();
            specialist.forEach(specialist1 -> {
                specialistResponses.add(new SpecialistResponse(specialist1.getId().toString(), specialist1.getName()));
            });
            return new ResponseEntity<>(specialistResponses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addDoctor")
    public ResponseEntity<?> addDoctor(@RequestBody AddDoctor addDoctor) {
        try {
            userDetailsService.saveDoctor(addDoctor, SecurityUtils.getUsername());
            return ResponseEntity.ok().body(new MessageResponse("Created!!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error!!"));
        }
    }
    @GetMapping("/getAllDoctors")
    public ResponseEntity<?> getAllDoctors(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "7") int size
    ) {
        try {
            Long hosId = userDetailsService.findByUsername(SecurityUtils.getUsername()).get().getHospitalCilinicMangager().getId();
            List<UserInformationForAdmin> userInformation = new ArrayList<>();
            List<UserEntity> userEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            Page<UserEntity> pageTuts = userDetailsService.findDoctorByHospital(keyword.trim(), hosId, pagingSort);
            userEntities = pageTuts.getContent();
            if (userEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userEntities.forEach(user -> {
                UserInformationForAdmin userInfo = new UserInformationForAdmin();
                BeanUtils.copyProperties(user, userInfo, "createDate", "birthday");
                userInfo.setSpecialist(user.getSpecialist().getName());
                userInfo.setGender(user.getGender().toString());
                userInfo.setStatus(user.getStatus().toString());
                userInfo.setCreateDate(user.getCreateDate().toString());
                userInfo.setBirthday(user.getBirthday().toString());
                userInformation.add(userInfo);
            });
            Map<String, Object> response = new HashMap<>();
            response.put("userInformation", userInformation);
            response.put("currentPage", pageTuts.getNumber() + 1);
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);

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
    public ResponseEntity<?> displayEditService(@RequestParam("idService") Long id) {
        try {
            ServicesEntity services;
            if (servicesService.findById(id).isEmpty()) {

                return new ResponseEntity<>(new MessageResponse("Not Found!"), HttpStatus.NOT_FOUND);
            }
            services = servicesService.findById(id).get();
            return new ResponseEntity<>(new Service(services.getId(), services.getName(), services.getDescription(), services.getPrice(), services.getDescription()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteServiceById")
    public ResponseEntity<?> deleteService(Long id) {
        try {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatus changeStatus) {
        try {
            servicesService.toggleStatus(changeStatus.getStatus(), Long.valueOf(changeStatus.getId()));
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getDoctorById")
    public ResponseEntity<?> getDoctor(@RequestParam("id")Long id){
        try {
            return new ResponseEntity<>(userDetailsService.getDoctorInfo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateDoctor")
    public ResponseEntity<?> getDoctor(@RequestBody EditDoctor editDoctor){
        try {
            userDetailsService.updateDoctorInfo(editDoctor);
            return new ResponseEntity<>(new MessageResponse("Update Succeeded "), HttpStatus.OK);
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
