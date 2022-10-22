package com.example.doctorcare.api.service;

import com.example.doctorcare.api.repository.TimeDoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeDoctorService {

    @Autowired
    private TimeDoctorRepository timeDoctorRepository;
}
