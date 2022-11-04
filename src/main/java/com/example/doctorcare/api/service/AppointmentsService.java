package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

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
                return appointmentsRepository.findByHostpital(id, pageable);
            } else if (beforeCreateDate != null && afterCreateDate == null) {
                return appointmentsRepository.findByCreateDateAfterAndHospital(id,beforeCreateDate, pageable);
            } else if (beforeCreateDate == null) {
                return appointmentsRepository.findByCreateDateBeforeAndHospital(id,afterCreateDate, pageable);
            } else {
                return appointmentsRepository.findByCreateDateBetweenAndHospital(id,beforeCreateDate, afterCreateDate, pageable);
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
            return appointmentsRepository.findByDoctorsId(id, pageable);
        } else if (beforeCreateDate != null && afterCreateDate == null) {
            return appointmentsRepository.findByCreateDateAfterAndDoctorId(id,beforeCreateDate, pageable);
        } else if (beforeCreateDate == null) {
            return appointmentsRepository.findByCreateDateBeforeAndDoctorId(id,afterCreateDate, pageable);
        } else {
            return appointmentsRepository.findByCreateDateBetweenAndDoctorId(id,beforeCreateDate, afterCreateDate, pageable);
        }
    }

    public String cancelAppointment(Long appointmentId){
        AppointmentsEntity appointment = findById(appointmentId);
        if (LocalDate.now().isBefore(appointment.getTimeDoctors().getDate())) {
            if (appointment.getStatus().equals(AppointmentStatus.PENDING)) {
                appointment.setStatus(AppointmentStatus.CANCEL);
                appointment.getTimeDoctors().setTimeDoctorStatus(TimeDoctorStatus.AVAILABLE);
                save(appointment);
                return "Success!!";
            }
            else return "Error!!";
        }
        else return "You are only allowed to cancel 1 day in advance!!";
    }
}
