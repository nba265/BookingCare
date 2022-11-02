package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.request.AddTimeDoctor;
import com.example.doctorcare.api.domain.dto.response.AppoinmentHistory;
import com.example.doctorcare.api.domain.dto.response.AppointmentHistoryForDoctor;
import com.example.doctorcare.api.domain.dto.response.TimeDoctor;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.service.AppointmentsService;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.TimeDoctorService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/doctor")
@PreAuthorize("hasRole('ROLE_DOCTOR')")
public class DoctorController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    TimeDoctorService timeDoctorService;
    @Autowired
    UserMapper userMapper;

    @Autowired
    AppointmentsService appointmentsService;

    PaginationAndSortUtil paginationAndSortUtil = new PaginationAndSortUtil();

    @Autowired
    HospitalClinicService hospitalClinicService;

    /*@GetMapping("/appointmentHistory")
    public ResponseEntity<?> appointmentHistory(
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
                appointmentHistory.setDescription(appointmentHistory.getDescription());
                appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
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
    }*/

    @PostMapping("/createTimeDoctors")
    public ResponseEntity<?> createTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
            TimeDoctors timeDoctors = new TimeDoctors();
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            timeDoctors.setDoctor(userMapper.convertToDto(user));
            timeDoctors.setTimeStart(timeDoctors1.getTimeStart());
            timeDoctors.setTimeEnd(timeDoctors1.getTimeEnd());
            timeDoctors.setDate(timeDoctors1.getCreateDate());
            timeDoctorService.save(timeDoctors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/getTimeDoctors")
    public ResponseEntity<?> getTimeDoctor() {
        UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
        List<TimeDoctors> timeDoctorsList = timeDoctorService.findAllByDoctor(user.getId());
        List<TimeDoctor> timeDoctors = new ArrayList<>();
        timeDoctorsList.forEach(timeDoctors1 -> {
            timeDoctors.add(new TimeDoctor(timeDoctors1.getId(), timeDoctors1.getTimeStart().toString(), timeDoctors1.getTimeEnd().toString(), timeDoctors1.getDate().toString()));
        });
        try {
            return new ResponseEntity<>(timeDoctors, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editTimeDoctors")
    public ResponseEntity<?> editTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
            TimeDoctors timeDoctors = new TimeDoctors();
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            timeDoctors.setDoctor(userMapper.convertToDto(user));
            timeDoctors.setTimeStart(timeDoctors1.getTimeStart());
            timeDoctors.setTimeEnd(timeDoctors1.getTimeEnd());
            timeDoctors.setDate(timeDoctors1.getCreateDate());
            timeDoctorService.save(timeDoctors);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/displayEditTimeDoctors")
    public ResponseEntity<?> editTimeDoctor(@RequestParam("id") Long id) {
        TimeDoctors timeDoctors = timeDoctorService.findById(id);
        try {
            return new ResponseEntity<>(new TimeDoctor(timeDoctors.getId(), timeDoctors.getTimeStart().toString(), timeDoctors.getTimeEnd().toString(), timeDoctors.getDate().toString()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTimeDoctors")
    public ResponseEntity<?> deleteTimeDoctor(@RequestParam("id") Long id) {
        try {
            timeDoctorService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/displayListAppoiment")
    public ResponseEntity<?> getAppointmentHistory(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(required = false) LocalDate before,
                                                   @RequestParam(required = false) LocalDate after) {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            List<AppoinmentHistory> appointmentHistories = new ArrayList<>();
            List<AppointmentsEntity> appointmentsEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            Page<AppointmentsEntity> pageTuts = appointmentsService.findByDoctorsId(user.getId(), pagingSort, before, after);
            appointmentsEntities = pageTuts.getContent();
            if (appointmentsEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            appointmentsEntities.forEach(appointments -> {
                AppoinmentHistory appointmentHistory = new AppoinmentHistory();
                appointmentHistory.setHospitalName(hospitalClinicService.findByAppointment_Id(appointments.getId()).getName());
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


    @GetMapping("/displayAppointmentInfo")
    public ResponseEntity<?> getAppointmentInfo(@RequestParam("timeDoctorId")Long id){
        try{
            AppointmentsEntity appointments = appointmentsService.findByTimeDoctorsId(id);
            return new ResponseEntity<>(new AppointmentHistoryForDoctor(appointments.getCustomers().getNamePatient(),
                    appointments.getCustomers().getPhonePatient(),
                    appointments.getCustomers().getBirthday().toString(),
                    appointments.getCustomers().getGender(),
                    userDetailsService.findById(appointments.getUser().getId()).getSpecialist().getName(),
                    appointments.getAppointmentCode()),HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
