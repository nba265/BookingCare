package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.request.AddTimeDoctor;
import com.example.doctorcare.api.domain.dto.response.*;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.event.OnSendAppointmentCancelEvent;
import com.example.doctorcare.api.exception.CancelAppointmentException;
import com.example.doctorcare.api.exception.TimeDoctorException;
import com.example.doctorcare.api.service.AppointmentsService;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.TimeDoctorService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import java.time.format.DateTimeFormatter;
import java.util.*;


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

    @Autowired
    private ApplicationEventPublisher eventPublisher;



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
            timeDoctorService.checkExistTimeDoctor(timeDoctors);
            timeDoctorService.save(timeDoctors);
            return new ResponseEntity<>(new MessageResponse("Time doctor has been created/updated successfully! \nPlease check it in history. "), HttpStatus.CREATED);
        }
        catch (TimeDoctorException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
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
            @RequestParam(required = false, defaultValue = "") String before,
            @RequestParam(required = false, defaultValue = "") String after
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
            Pageable pagingSort = PageRequest.of(page - 1, size, Sort.by("date").ascending().and(Sort.by("timeStart")));
            Page<TimeDoctorsEntity> pageTuts = timeDoctorService.findByDoctorsId(user.getId(), pagingSort, before1, after1);
            timeDoctorsList = pageTuts.getContent();
            if (timeDoctorsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            /* List<TimeDoctor> finalTimeDoctors = timeDoctors;*/
            timeDoctorsList.forEach(timeDoctors1 -> timeDoctors.add(new TimeDoctor(timeDoctors1.getId(), timeDoctors1.getTimeStart().toString(), timeDoctors1.getTimeEnd().toString(), timeDoctors1.getDate().toString(), timeDoctors1.getTimeDoctorStatus().toString())));

            /*timeDoctors = finalTimeDoctors.stream().sorted(Comparator.comparing(TimeDoctor::getDate).thenComparing(TimeDoctor::getTimeStart)).collect(Collectors.toList());*/

            Map<String, Object> response = new HashMap<>();
            response.put("timeDoctorLists", timeDoctors);
            response.put("currentPage", pageTuts.getNumber() + 1);
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            timeDoctors.forEach(timeDoctor -> System.out.println(timeDoctor.getTimeStart()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/displayListAppointment")
    public ResponseEntity<?> getAppointmentHistory(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "7") int size,
                                                   @RequestParam(required = false,defaultValue = "") String before,
                                                   @RequestParam(required = false,defaultValue = "") String after) {
        try {
            System.out.println(page);
            UserEntity user = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            List<AppointmentHistory> appointmentHistories = new ArrayList<>();
            List<AppointmentsEntity> appointmentsEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, new String[]{"createDate","desc"});
        /*    LocalDate before1 = null;
            LocalDate after1 = null;
            if (before != null) {
                before1 = LocalDate.parse(before);
                before1 = LocalDate.parse(before);
            }
            if (after != null) {
                after1 = LocalDate.parse(after);
            }*/
            Page<AppointmentsEntity> pageTuts = appointmentsService.findByDoctorsId(user.getId(), pagingSort,
                    !Objects.equals(before, "") ?LocalDate.parse(before):null, !Objects.equals(after, "") ?LocalDate.parse(after):null);
            appointmentsEntities = pageTuts.getContent();
            if (appointmentsEntities.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse("You don't have any appointments"), HttpStatus.NO_CONTENT);
            }
            appointmentsEntities.forEach(appointments -> {
                AppointmentHistory appointmentHistory = new AppointmentHistory();
                appointmentHistory.setHospitalName(user.getHospitalCilinicDoctor().getName());
                appointmentHistory.setHospitalPhone(user.getHospitalCilinicDoctor().getPhone());
                appointmentHistory.setHospitalAddress(user.getHospitalCilinicDoctor().getAddress());
                appointmentHistory.setId(appointments.getId());
                if (appointments.getStatus().equals(AppointmentStatus.CANCEL)) {
                    appointmentHistory.setDate(appointments.getCancelTimeDoctors().getDate().toString());
                    appointmentHistory.setTimeStart(appointments.getCancelTimeDoctors().getTimeStart().toString());
                    appointmentHistory.setTimeEnd(appointments.getCancelTimeDoctors().getTimeEnd().toString());
                } else {
                    appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                    appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                    appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
                }
                appointmentHistory.setCreateDate(appointments.getCreateDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
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
            Optional<UserEntity> user = userDetailsService.findByUsername(SecurityUtils.getUsername());
            if (user.isPresent()) {
                AppointmentsEntity appointment = appointmentsService.findById(id);
                if (user.get().getId().equals(appointment.getStatus().equals(AppointmentStatus.CANCEL) ?
                        appointment.getCancelTimeDoctors().getDoctor().getId() : appointment.getTimeDoctors().getDoctor().getId())) {
                    User doctor = userDetailsService.findByTimeDoctorId(appointment.getStatus().equals(AppointmentStatus.CANCEL) ? appointment.getCancelTimeDoctors().getId() : appointment.getTimeDoctors().getId());
                    return new ResponseEntity<>(appointmentsService.setAppointmentInfoForDoctor(doctor, appointment), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(new MessageResponse("Not Found!"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cancelAppointment")
    public ResponseEntity<?> cancelAppointment(@RequestParam(name = "appointmentCode") String code, @RequestParam(name="reason") String reason) {
        try {
            Optional<AppointmentsEntity> appointment = appointmentsService.findByCode(code);
            if (appointment.isPresent()) {
                appointment.get().setCancelReason(reason);
                AppointmentInfoForUser appointmentInfoForUser = appointmentsService.setAppointmentInfoForUser(userDetailsService.findByTimeDoctorId(appointment.get().getTimeDoctors().getId()),appointment.get());
                if (appointmentsService.cancelAppointmentForDoctor(appointment.get())) {
                    eventPublisher.publishEvent(new OnSendAppointmentCancelEvent(this, appointment.get().getUser().getUsername().trim(),
                            appointmentInfoForUser));
                }
                return new ResponseEntity<>(new MessageResponse("Success!"), HttpStatus.OK);
            } else return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (CancelAppointmentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
