package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.AppointmentMapper;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
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

    public Page<AppointmentsEntity> findByHospitalCustomerCreateDate(Long id, Pageable pageable, String bookName, LocalDate before, LocalDate after) {
        LocalDateTime beforeCreateDate;
        LocalDateTime afterCreateDate;
        if (before == null)
        {
            beforeCreateDate=null;
        }
        else beforeCreateDate=before.atStartOfDay();
        if (after==null){
            afterCreateDate=null;
        }
        else afterCreateDate=after.atStartOfDay();
        if (bookName == null) {
            if (beforeCreateDate == null && afterCreateDate == null) {
                return appointmentsRepository.findByHostpital(id, pageable);
            } else if (beforeCreateDate != null && afterCreateDate == null) {
                return appointmentsRepository.findByCreateDateAfter(beforeCreateDate, pageable);
            } else if (beforeCreateDate == null && afterCreateDate != null) {
                return appointmentsRepository.findByCreateDateBefore(afterCreateDate, pageable);
            } else {
                return appointmentsRepository.findByCreateDateBetween(beforeCreateDate, afterCreateDate,pageable);
            }
        } else {
            if (beforeCreateDate == null && afterCreateDate == null) {
                return appointmentsRepository.findByUser_FullNameContainingIgnoreCase(bookName,pageable);
            } else if (beforeCreateDate != null && afterCreateDate == null) {
                return appointmentsRepository.findByUser_FullNameContainingIgnoreCaseAndCreateDateAfter(bookName, beforeCreateDate,pageable);
            } else if (beforeCreateDate == null && afterCreateDate != null) {
                return appointmentsRepository.findByUser_FullNameContainingIgnoreCaseAndCreateDateAfter(bookName, afterCreateDate,pageable);
            } else {
                return appointmentsRepository.findByUser_FullNameContainingIgnoreCaseAndCreateDateBetween(bookName, beforeCreateDate, afterCreateDate,pageable);
            }
        }
    }

}
