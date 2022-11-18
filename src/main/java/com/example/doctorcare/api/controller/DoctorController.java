package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.request.AddTimeDoctor;
import com.example.doctorcare.api.domain.dto.response.AppoinmentHistory;
import com.example.doctorcare.api.domain.dto.response.AppointmentHistoryForDoctor;
import com.example.doctorcare.api.domain.dto.response.MessageResponse;
import com.example.doctorcare.api.domain.dto.response.TimeDoctor;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.service.AppointmentsService;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.TimeDoctorService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


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

    @PostMapping("/editTimeDoctors")
    public ResponseEntity<?> editTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
            TimeDoctorsEntity timeDoctors;
            if (timeDoctors1.getId() != null) {
                timeDoctors = timeDoctorService.findById(timeDoctors1.getId());
            } else {
                timeDoctors = new TimeDoctorsEntity();
                UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
                timeDoctors.setDoctor(user);
            }
            timeDoctors.setTimeDoctorStatus(TimeDoctorStatus.AVAILABLE);
            timeDoctors.setTimeStart(LocalTime.parse(timeDoctors1.getTimeStart()));
            timeDoctors.setTimeEnd(LocalTime.parse(timeDoctors1.getTimeEnd()));
            timeDoctors.setDate(LocalDate.parse(timeDoctors1.getCreateDate()));
            timeDoctorService.save(timeDoctors);
            return new ResponseEntity<>(new MessageResponse("Time doctor has been created/updated successfully! \nPlease check it in history. "), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createTimeDoctors")
    public ResponseEntity<?> createTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
                    UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            timeDoctorService.addTimeDoctor(timeDoctors1, user);
            return new ResponseEntity<>(new MessageResponse("Time doctor has been created successfully! \nPlease check it in history."), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getTimeDoctors")
    public ResponseEntity<?> getTimeDoctor(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(required = false,defaultValue = "") String before,
            @RequestParam(required = false,defaultValue = "") String after
    ) {
        try {
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            List<TimeDoctorsEntity> timeDoctorsList;
            List<TimeDoctor> timeDoctors = new ArrayList<>();
            LocalDate before1 = null;
            LocalDate after1 = null;
            if (!Objects.equals(before, "")) {
                before1 = LocalDate.parse(before);
            }
            if (!Objects.equals(after, "")) {
                after1 = LocalDate.parse(after);
            }
            Pageable pagingSort = PageRequest.of(page - 1, 7, Sort.by("date").ascending().and(Sort.by("timeStart")));
            Page<TimeDoctorsEntity> pageTuts = timeDoctorService.findByDoctorsId(user.getId(), pagingSort, before1, after1);
            timeDoctorsList = pageTuts.getContent();
            if (timeDoctorsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            /* List<TimeDoctor> finalTimeDoctors = timeDoctors;*/
            timeDoctorsList.forEach(timeDoctors1 -> {
                timeDoctors.add(new TimeDoctor(timeDoctors1.getId(), timeDoctors1.getTimeStart().toString(), timeDoctors1.getTimeEnd().toString(), timeDoctors1.getDate().toString(), timeDoctors1.getTimeDoctorStatus().toString()));
            });

            /*timeDoctors = finalTimeDoctors.stream().sorted(Comparator.comparing(TimeDoctor::getDate).thenComparing(TimeDoctor::getTimeStart)).collect(Collectors.toList());*/

            Map<String, Object> response = new HashMap<>();
            response.put("timeDoctorLists", timeDoctors);
            response.put("currentPage", pageTuts.getNumber() + 1);
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            timeDoctors.forEach(timeDoctor -> {
                System.out.println(timeDoctor.getTimeStart());
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PutMapping("/editTimeDoctors")
    public ResponseEntity<?> editTimeDoctor(@RequestBody AddTimeDoctor timeDoctors1) {
        try {
            TimeDoctors timeDoctors = new TimeDoctors();
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            timeDoctors.setDoctor(userMapper.convertToDto(user));
            timeDoctors.setTimeStart(LocalTime.parse(timeDoctors1.getTimeStart()));
            timeDoctors.setTimeEnd(LocalTime.parse(timeDoctors1.getTimeEnd()));
            timeDoctors.setDate(LocalDate.parse(timeDoctors1.getCreateDate()));
            timeDoctorService.save(timeDoctors);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
*/
    /*@GetMapping("/displayEditTimeDoctors")
    public ResponseEntity<?> editTimeDoctor(@RequestParam("id") Long id) {
        TimeDoctors timeDoctors = timeDoctorService.findById(id);
        try {
            return new ResponseEntity<>(new TimeDoctor(timeDoctors.getId(), timeDoctors.getTimeStart().toString(), timeDoctors.getTimeEnd().toString(), timeDoctors.getDate().toString(), timeDoctors.getTimeDoctorStatus().toString()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }*/

/*    @DeleteMapping("/deleteTimeDoctors")
    public ResponseEntity<?> deleteTimeDoctor(@RequestParam("id") Long id) {
        try {
            timeDoctorService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }*/

    @GetMapping("/displayListAppointment")
    public ResponseEntity<?> getAppointmentHistory(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "7") int size,
                                                   @RequestParam(required = false) String before,
                                                   @RequestParam(required = false) String after) {
        try {
            System.out.println(page);
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            List<AppoinmentHistory> appointmentHistories = new ArrayList<>();
            List<AppointmentsEntity> appointmentsEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            LocalDate before1 = null;
            LocalDate after1 = null;

            if (before != null) {
                before1 = LocalDate.parse(before);

            }
            if (after != null) {
                after1 = LocalDate.parse(after);

            }
            /*      System.out.println(after1);*/
            Page<AppointmentsEntity> pageTuts = appointmentsService.findByDoctorsId(user.getId(), pagingSort, before1, after1);
            appointmentsEntities = pageTuts.getContent();
            if (appointmentsEntities.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse("You don't have any appointments"),HttpStatus.NO_CONTENT);
            }
            appointmentsEntities.forEach(appointments -> {
                AppoinmentHistory appointmentHistory = new AppoinmentHistory();
                appointmentHistory.setHospitalName(hospitalClinicService.findByAppointment_Id(appointments.getId()).getName());
                appointmentHistory.setId(appointments.getId());
                appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
                appointmentHistory.setAppointmentCode(appointments.getAppointmentCode());
                appointmentHistory.setStatus(appointments.getStatus().toString());
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
    public ResponseEntity<?> getAppointmentInfo(@RequestParam("appointmentId") Long id) {
        try {
            AppointmentsEntity appointments = appointmentsService.findById(id);
            return new ResponseEntity<>(new AppointmentHistoryForDoctor(appointments.getCustomers().getNamePatient(),
                    appointments.getCustomers().getPhonePatient(),
                    appointments.getCustomers().getBirthday().toString(),
                    appointments.getCustomers().getGender(),
                    userDetailsService.findByTimeDoctorId(appointments.getTimeDoctors().getId()).getSpecialist().getName(),
                    appointments.getAppointmentCode(),
                    appointments.getStatus().toString(),
                    appointments.getDescription(), appointments.getServices().getName()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
