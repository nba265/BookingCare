package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.repository.TimeDoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeDoctorService {

    @Autowired
    private TimeDoctorRepository timeDoctorRepository;
    @Autowired
    private TimeDoctorsMapper timeDoctorsMapper;
    public void save(TimeDoctors timeDoctors){
        timeDoctorRepository.save(timeDoctorsMapper.convertToEntity(timeDoctors));
    }
    public List<TimeDoctors> findAllByDoctor(Long doctorId){
        List<TimeDoctors> timeDoctorsList= new ArrayList<>();
        timeDoctorRepository.findByDoctor_Id(doctorId).forEach(timeDoctors -> {timeDoctorsList.add(timeDoctorsMapper.convertToDto(timeDoctors));});
    return timeDoctorsList;
    }
    public TimeDoctors findById(Long id){
        return timeDoctorsMapper.convertToDto(timeDoctorRepository.findById(id).get());
    }

    public List<TimeDoctors> findFreeTimeByDoctorId(Long id){
        List<TimeDoctors> timeDoctors = findAllByDoctor(id);
        timeDoctors.forEach(timeDoctors1 -> {
            if (timeDoctors1.getAppointments() != null) timeDoctors.remove(timeDoctors1);
        });
        return timeDoctors;
    }
}
