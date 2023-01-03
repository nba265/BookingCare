package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.response.AppointmentHistory;
import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.repository.AppointmentsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private HospitalClinicService hospitalClinicService;

    public void save(AppointmentsEntity appointments) {
        appointmentsRepository.save(appointments);
    }


    public AppointmentsEntity findById(Long id) {
        return appointmentsRepository.findById(id).get();
    }

    public Page<AppointmentsEntity> findByHospitalCustomerCreateDate(Long id, Pageable pageable, LocalDate before, LocalDate after) {
        LocalDateTime beforeCreateDate;
        LocalDateTime afterCreateDate;
        if (before == null) {
            beforeCreateDate = null;
        } else beforeCreateDate = before.atStartOfDay();
        if (after == null) {
            afterCreateDate = null;
        } else afterCreateDate = after.atStartOfDay();

        if (beforeCreateDate == null && afterCreateDate == null) {
            return appointmentsRepository.findByServices_HospitalCilinic_Id(id, pageable);
        } else if (beforeCreateDate != null && afterCreateDate == null) {
            return appointmentsRepository.findByServices_HospitalCilinic_IdAndCreateDateAfter(id, beforeCreateDate, pageable);
        } else if (beforeCreateDate == null) {
            return appointmentsRepository.findByServices_HospitalCilinic_IdAndCreateDateBefore(id, afterCreateDate, pageable);
        } else {
            return appointmentsRepository.findByServices_HospitalCilinic_IdAndCreateDateBetween(id, beforeCreateDate, afterCreateDate, pageable);
        }

    }

    public Page<AppointmentsEntity> findByUserCreateDate(Long id, Pageable pageable, LocalDate before, LocalDate after) {
        LocalDateTime beforeCreateDate;
        LocalDateTime afterCreateDate;
        if (before == null) {
            beforeCreateDate = null;
        } else beforeCreateDate = before.atStartOfDay();
        if (after == null) {
            afterCreateDate = null;
        } else afterCreateDate = after.atStartOfDay();

        if (beforeCreateDate == null && afterCreateDate == null) {
            return appointmentsRepository.findByUser_Id(id, pageable);
        } else if (beforeCreateDate != null && afterCreateDate == null) {
            return appointmentsRepository.findByUser_IdAndCreateDateAfter(id, beforeCreateDate, pageable);
        } else if (beforeCreateDate == null) {
            return appointmentsRepository.findByUser_IdAndCreateDateBefore(id, afterCreateDate, pageable);
        } else {
            return appointmentsRepository.findByUser_IdAndCreateDateBetween(id, beforeCreateDate, afterCreateDate, pageable);
        }

    }

    public AppointmentsEntity findByTimeDoctorsId(Long id) {
        return appointmentsRepository.findByTimeDoctors_Id(id);
    }

    public Page<AppointmentsEntity> findByDoctorsId(Long id, Pageable pageable, LocalDate before, LocalDate after) {
        LocalDateTime beforeCreateDate;
        LocalDateTime afterCreateDate;
        if (before == null) {
            beforeCreateDate = null;
        } else beforeCreateDate = before.atStartOfDay();
        if (after == null) {
            afterCreateDate = null;
        } else afterCreateDate = after.atStartOfDay();
        if (beforeCreateDate == null && afterCreateDate == null) {
            return appointmentsRepository.findByTimeDoctors_Doctor_Id(id, pageable);
        } else if (beforeCreateDate != null && afterCreateDate == null) {
            return appointmentsRepository.findByTimeDoctors_Doctor_IdAndCreateDateAfter(id, beforeCreateDate, pageable);
        } else if (beforeCreateDate == null) {
            return appointmentsRepository.findByTimeDoctors_Doctor_IdAndCreateDateBefore(id, afterCreateDate, pageable);
        } else {
            return appointmentsRepository.findByTimeDoctors_Doctor_IdAndCreateDateBetween(id, beforeCreateDate, afterCreateDate, pageable);
        }
    }

    public String cancelAppointment(String appointmentCode,String reason) {
        Optional<AppointmentsEntity> appointment = appointmentsRepository.findByAppointmentCode(appointmentCode);
        if (appointment.isPresent()) {
            if (LocalDate.now().isBefore(appointment.get().getTimeDoctors().getDate())) {
                if (appointment.get().getStatus().equals(AppointmentStatus.PENDING)) {
                    appointment.get().setStatus(AppointmentStatus.CANCEL);
                    appointment.get().setCancelTimeDoctors(appointment.get().getTimeDoctors());
                    appointment.get().getCancelTimeDoctors().setTimeDoctorStatus(TimeDoctorStatus.AVAILABLE);
                    appointment.get().setTimeDoctors(null);
                    appointment.get().setCancelReason(reason);
                    save(appointment.get());
                    return "Success!";
                } else return "You are only allowed to cancel PENDING appointment!";
            } else return "You are only allowed to cancel 1 day in advance!";
        } else return "Wrong Code!";
    }

    public Optional<AppointmentsEntity> findByCode(String code) {
        return appointmentsRepository.findByAppointmentCode(code);
    }

    public String cancelAppointmentForDoctor(AppointmentsEntity appointment) {
        if (LocalDate.now().isBefore(appointment.getTimeDoctors().getDate())) {
            if (appointment.getStatus().equals(AppointmentStatus.PENDING)) {
                appointment.setStatus(AppointmentStatus.CANCEL);
                appointment.setCancelTimeDoctors(appointment.getTimeDoctors());
                appointment.getCancelTimeDoctors().setTimeDoctorStatus(TimeDoctorStatus.AVAILABLE);
                appointment.setTimeDoctors(null);
                save(appointment);
                return "Success!";
            } else return "You are only allowed to cancel PENDING appointment!";
        } else return "You are only allowed to cancel 1 day in advance!";
    }


    public AppointmentInfoForUser setAppointmentInfoForUser(User doctor, AppointmentsEntity appointment) {
        AppointmentInfoForUser appointmentInfoForUser = new AppointmentInfoForUser();
        appointmentInfoForUser.setDoctorName(doctor.getFullName());
        appointmentInfoForUser.setPhoneDoctor(doctor.getPhone());
        appointmentInfoForUser.setGenderDoctor(doctor.getGender().toString());
        appointmentInfoForUser.setSpecialistDoctor(doctor.getSpecialist().getName());
        appointmentInfoForUser.setGenderCustomer(appointment.getCustomers().getGender().toString());
        appointmentInfoForUser.setBirthday(appointment.getCustomers().getBirthday().toString());
        appointmentInfoForUser.setNamePatient(appointment.getCustomers().getNamePatient());
        appointmentInfoForUser.setPhonePatient(appointment.getCustomers().getPhonePatient());
        appointmentInfoForUser.setDescription(appointment.getDescription());
        appointmentInfoForUser.setStatus(appointment.getStatus());
        appointmentInfoForUser.setService(appointment.getServices().getName());
        appointmentInfoForUser.setPrice(appointment.getServices().getPrice().toString());
        appointmentInfoForUser.setDoctorEmail(doctor.getEmail());
        appointmentInfoForUser.setCancelReason(appointment.getCancelReason());
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setId(appointment.getId());
        HospitalClinicEntity hospitalClinicEntity = hospitalClinicService.findByAppointment_Id(appointment.getId(), appointment.getStatus());
        appointmentHistory.setHospitalName(hospitalClinicEntity.getName());
        appointmentHistory.setHospitalPhone(hospitalClinicEntity.getPhone());
        appointmentHistory.setHospitalAddress(hospitalClinicEntity.getAddress());
        appointmentHistory.setCreateDate(appointment.getCreateDate().toString());
        if (appointment.getStatus().equals(AppointmentStatus.CANCEL)) {
            appointmentHistory.setDate(appointment.getCancelTimeDoctors().getDate().toString());
            appointmentHistory.setTimeStart(appointment.getCancelTimeDoctors().getTimeStart().toString());
            appointmentHistory.setTimeEnd(appointment.getCancelTimeDoctors().getTimeEnd().toString());
        } else {
            appointmentHistory.setDate(appointment.getTimeDoctors().getDate().toString());
            appointmentHistory.setTimeStart(appointment.getTimeDoctors().getTimeStart().toString());
            appointmentHistory.setTimeEnd(appointment.getTimeDoctors().getTimeEnd().toString());
        }
        appointmentHistory.setAppointmentCode(appointment.getAppointmentCode());
        appointmentHistory.setStatus(appointment.getStatus().toString());
        appointmentInfoForUser.setAppointmentHistory(appointmentHistory);
        return appointmentInfoForUser;
    }
}
