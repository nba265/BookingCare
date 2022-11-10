package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.repository.TimeDoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TimeDoctorService {

    @Autowired
    private TimeDoctorRepository timeDoctorRepository;
    @Autowired
    private TimeDoctorsMapper timeDoctorsMapper;

    public void save(TimeDoctorsEntity timeDoctors) {
        timeDoctorRepository.save(timeDoctors);
    }

    public void saveEntity(TimeDoctorsEntity timeDoctors) {
        timeDoctorRepository.save(timeDoctors);
    }

    public List<TimeDoctors> findAllByDoctor(Long doctorId, LocalDate date) {
        List<TimeDoctors> timeDoctorsList = new ArrayList<>();
        timeDoctorRepository.findByDoctor_IdAndTimeStamp(doctorId).forEach(timeDoctors -> timeDoctorsList.add(timeDoctorsMapper.convertToDto(timeDoctors)));
        if (date != null) {
            timeDoctorsList.removeIf(timeDoctors -> !date.isEqual(timeDoctors.getDate()));
        }
        return timeDoctorsList;
    }

    public List<TimeDoctors> findByDoctor_IdAndTimeStampAndStatus(Long doctorId, TimeDoctorStatus timeDoctorStatus) {
        List<TimeDoctors> timeDoctorsList = new ArrayList<>();
        timeDoctorRepository.findByDoctor_IdAndTimeStampAndStatus(doctorId, timeDoctorStatus.toString()).forEach(timeDoctors -> timeDoctorsList.add(timeDoctorsMapper.convertToDto(timeDoctors)));
        return timeDoctorsList;
    }


    public TimeDoctorsEntity findById(Long id) {
        return timeDoctorRepository.findById(id).get();
    }

    public TimeDoctorsEntity findByIdEntity(Long id) {
        return timeDoctorRepository.findById(id).get();
    }

/*    public List<TimeDoctors> findFreeTimeByDoctorId(Long id) {
        List<TimeDoctors> timeDoctors = findAllByDoctor(id);
        timeDoctors.forEach(timeDoctors1 -> {
            if (timeDoctors1.getAppointments() != null) timeDoctors.remove(timeDoctors1);
        });
        return timeDoctors;
    }*/

    public void deleteById(Long id) {
        timeDoctorRepository.deleteById(id);
    }

    public Page<TimeDoctorsEntity> findByDoctorsId(Long id, Pageable pageable, LocalDate before, LocalDate after) {
        LocalDate beforeCreateDate;
        if (before == null) {
            beforeCreateDate = null;
        } else if (before.isBefore(LocalDate.now())) {
            beforeCreateDate = LocalDate.now();
        } else {
            beforeCreateDate = before;
        }
        if (beforeCreateDate == null && after == null) {
            return timeDoctorRepository.findByDoctor_IdAndDateIsAfter(id, LocalDate.now(), pageable);
        } else if (beforeCreateDate != null && after == null) {
            return timeDoctorRepository.findByDoctor_IdAndDateIsAfter(id, beforeCreateDate, pageable);
        } else
            return timeDoctorRepository.findByDoctor_IdAndDateBetween(id, Objects.requireNonNullElseGet(beforeCreateDate, LocalDate::now), after, pageable);
    }
}
