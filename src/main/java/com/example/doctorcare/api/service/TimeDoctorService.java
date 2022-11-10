package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.repository.TimeDoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<TimeDoctors> findAllByDoctor(Long doctorId) {
        List<TimeDoctors> timeDoctorsList = new ArrayList<>();
        timeDoctorRepository.findByDoctor_IdAndTimeStamp(doctorId).forEach(timeDoctors -> timeDoctorsList.add(timeDoctorsMapper.convertToDto(timeDoctors)));
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

    public List<TimeDoctors> findFreeTimeByDoctorId(Long id) {
        List<TimeDoctors> timeDoctors = findAllByDoctor(id);
        timeDoctors.forEach(timeDoctors1 -> {
            if (timeDoctors1.getAppointments() != null) timeDoctors.remove(timeDoctors1);
        });
        return timeDoctors;
    }

    public void deleteById(Long id) {
        timeDoctorRepository.deleteById(id);
    }

}
