package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.request.MakeAppointment;
import com.example.doctorcare.api.domain.dto.response.*;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.CustomersEntity;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.event.OnSendAppointmentInfoEvent;
import com.example.doctorcare.api.service.*;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import com.example.doctorcare.api.utilis.RandomStringGenaration;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    PaginationAndSortUtil paginationAndSortUtil = new PaginationAndSortUtil();

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
    public ResponseEntity<?> searchHospital(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, @RequestParam(name = "districtCode", required = false, defaultValue = "") String districtCode) {
        try {
            List<HospitalClinicInfoResponse> responses = new ArrayList<>();
            hospitalClinicService.findByKeywordsOrDistrictCode(keyword, districtCode).forEach(hospitalClinic -> responses.add(new HospitalClinicInfoResponse(hospitalClinic.getId(), hospitalClinic.getName(), hospitalClinic.getAddress(), hospitalClinic.getPhone(), null, hospitalClinic.getDistrictCode())));
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listSpecialist")
    public ResponseEntity<?> allSpecialistInHospital(@RequestParam("hosId") Long id) {
        try {
            return new ResponseEntity<>(specialistService.findAllByHospitalCilinicId(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<?> findDoctor(@RequestParam(name = "hosId", required = false,defaultValue = "0") Long hosId, @RequestParam(name = "specId", required = false,defaultValue = "0") Long specId, @RequestParam(name = "gender", required = false,defaultValue = "") String gender, @RequestParam(name = "keyword", required = false,defaultValue = "") String keyword) {
        try {
            List<DoctorInfoResponse> doctorInfoResponses = new ArrayList<>();
            userDetailsService.findDoctor(hosId, specId, gender, keyword).forEach(user -> doctorInfoResponses.add(new DoctorInfoResponse(user.getId(), user.getFullName(), user.getGender(), user.getDegree(), user.getNationality(), user.getExperience(), user.getSpecialist().getName())));
            return new ResponseEntity<>(doctorInfoResponses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listTimeDoctor")
    public ResponseEntity<?> findTimeDoctor(@RequestParam(name = "date") String date) {
        try {
            List<TimeDoctor> timeDoctorsResponse =timeDoctorService.findByDateAndStatus(LocalDate.parse(date));
            return new ResponseEntity<>(timeDoctorsResponse, HttpStatus.OK);
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
    public ResponseEntity<?> bookingServiceAndDatetime(@RequestParam("doctorId") Long doctorId) {
        try {
            DoctorSearchInfo doctorSearchInfo = new DoctorSearchInfo();
            User doctor = userDetailsService.findDoctorById(doctorId);
            doctorSearchInfo.setDoctorName(doctor.getFullName());
            doctorSearchInfo.setServicesList(servicesService.findAllByHospitalCilinic_Id(doctor.getHospitalClinicDoctor().getId()));
            doctorSearchInfo.setHospName(doctor.getHospitalClinicDoctor().getName());
            doctorSearchInfo.setSpecialist(doctor.getSpecialist().getName());
            doctorSearchInfo.setDoctorId(doctorId);
            List<TimeDoctors> timeDoctorsList = timeDoctorService.findByDoctor_IdAndTimeStampAndStatus(doctorId, TimeDoctorStatus.AVAILABLE);
            Set<String> dayUnvailable = new HashSet<>();
            timeDoctorsList.forEach(timeDoctors -> {
                TimeDoctor timeDoctor = new TimeDoctor();
                timeDoctor.setTimeEnd(timeDoctors.getTimeEnd().toString());
                timeDoctor.setTimeStart(timeDoctors.getTimeStart().toString());
                timeDoctor.setId(timeDoctors.getId());
                timeDoctor.setDate(timeDoctors.getDate().toString());
                timeDoctor.setTimeDoctorStatus(timeDoctors.getTimeDoctorStatus().toString());
                doctorSearchInfo.getTimeDoctorsAvailable().add(timeDoctor);
                dayUnvailable.add(timeDoctors.getDate().toString());
            });
            doctorSearchInfo.setDayUnavailable(dayUnvailable.stream().sorted(Comparator.comparing(LocalDate::parse)).collect(Collectors.toCollection(LinkedHashSet::new)));
            System.out.println(dayUnvailable);
            doctorSearchInfo.setHosId(doctor.getHospitalClinicDoctor().getId());
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
            appointments.getTimeDoctors().setTimeDoctorStatus(TimeDoctorStatus.UNAVAILABLE);
            appointments.setServices(servicesService.findById(makeAppointment.getServId()).get());
            CustomersEntity customers = new CustomersEntity();
            customers.setEmail(user.getEmail());
            customers.setNameBooking(user.getFullName());
            customers.setPhoneBooking(user.getPhone());
            customers.setGender(makeAppointment.getGender());
            customers.setBirthday(LocalDate.parse(makeAppointment.getBirthday()));
            customers.setPhonePatient(makeAppointment.getPhone());
            customers.setNamePatient(makeAppointment.getFullName());
            customers.setIdentityCard(makeAppointment.getIdentityCard());
            appointments.setCustomers(customers);
            appointments.setDescription(makeAppointment.getDescription());
            appointments.setStatus(AppointmentStatus.PENDING);
            appointments.setUser(user);
            appointments.setAppointmentCode(RandomStringGenaration.randomStringWithLength(10));
            appointmentsService.save(appointments);

            eventPublisher.publishEvent(new OnSendAppointmentInfoEvent(this, SecurityUtils.getUsername().trim(),
                    appointmentsService.setAppointmentInfoForUser(userDetailsService.findByTimeDoctorId(makeAppointment.getTimeDoctorId()), appointments)));

            return new ResponseEntity<>(new MessageResponse("Appointment created successfully! \nPlease check it in history. "), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/appointmentHistory")
    public ResponseEntity<?> appointmentHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(required = false) String before,
            @RequestParam(required = false) String after
    ) {
        try {
            UserEntity userEntity = userDetailsService.findByUsername(SecurityUtils.getUsername()).get();
            List<AppointmentHistory> appointmentHistories = new ArrayList<>();
            List<AppointmentsEntity> appointmentsEntities;
            LocalDate before1 = null;
            LocalDate after1 = null;
            if (before != null) {
                before1 = LocalDate.parse(before);
            }
            if (after != null) {
                after1 = LocalDate.parse(after);
            }
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            Page<AppointmentsEntity> pageTuts = appointmentsService.findByUserCreateDate(userEntity.getId(), pagingSort, before1, after1);
            appointmentsEntities = pageTuts.getContent();
            if (appointmentsEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            appointmentsEntities.forEach(appointments -> {
                AppointmentHistory appointmentHistory = new AppointmentHistory();
                appointmentHistory.setId(appointments.getId());
                HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByAppointment_Id(appointments.getId(),appointments.getStatus());
                appointmentHistory.setHospitalName(hospitalClinicEntity.getName());
                appointmentHistory.setHospitalPhone(hospitalClinicEntity.getPhone());
                appointmentHistory.setHospitalAddress(hospitalClinicEntity.getAddress());
                if (appointments.getStatus().equals(AppointmentStatus.CANCEL)) {
                    appointmentHistory.setDate(appointments.getCancelTimeDoctors().getDate().toString());
                    appointmentHistory.setTimeStart(appointments.getCancelTimeDoctors().getTimeStart().toString());
                    appointmentHistory.setTimeEnd(appointments.getCancelTimeDoctors().getTimeEnd().toString());
                } else {
                    appointmentHistory.setDate(appointments.getTimeDoctors().getDate().toString());
                    appointmentHistory.setTimeStart(appointments.getTimeDoctors().getTimeStart().toString());
                    appointmentHistory.setTimeEnd(appointments.getTimeDoctors().getTimeEnd().toString());
                }
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

    @GetMapping("/appointmentInfo")
    public ResponseEntity<?> appointmentInfo(@RequestParam("appointmentId") Long id) {
        try {
            Optional<UserEntity> user = userDetailsService.findByUsername(SecurityUtils.getUsername());
            if (user.isPresent()) {
                AppointmentsEntity appointment = appointmentsService.findById(id);
                if (user.get().getId().equals(appointment.getUser().getId())) {
                    User doctor = userDetailsService.findByTimeDoctorId(appointment.getTimeDoctors().getId());
                    return new ResponseEntity<>(appointmentsService.setAppointmentInfoForUser(doctor, appointment), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(new MessageResponse("Not Found!"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cancelAppointment")
    public ResponseEntity<?> doCancelAppointment(@RequestParam("appointmentId") Long id) {
        try {
            return new ResponseEntity<>(new MessageResponse(appointmentsService.cancelAppointment(id)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
