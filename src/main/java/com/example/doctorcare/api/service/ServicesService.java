package com.example.doctorcare.api.service;

import com.example.doctorcare.api.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    public List<Service> findAllByDoctorId(Long id){

    }
}
