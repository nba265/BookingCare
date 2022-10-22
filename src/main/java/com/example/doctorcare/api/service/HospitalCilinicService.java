package com.example.doctorcare.api.service;

import com.example.doctorcare.api.repository.HospitalCilinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalCilinicService {

    @Autowired
    private HospitalCilinicRepository hospitalCilinicRepository;
}
