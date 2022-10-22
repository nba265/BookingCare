package com.example.doctorcare.api.service;

import com.example.doctorcare.api.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;
}
