package com.example.doctorcare.api.service;

import com.example.doctorcare.api.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialistService {

    @Autowired
    private SpecialistRepository specialistRepository;
}
