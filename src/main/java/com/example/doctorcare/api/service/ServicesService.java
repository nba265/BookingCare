package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import com.example.doctorcare.api.repository.HospitalCilinicRepository;
import com.example.doctorcare.api.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private HospitalCilinicRepository hospitalCilinicRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    public List<Services> findAllByHospitalCilinic_Id(Long id){

        return serviceMapper.convertToDtoList(servicesRepository.findAllByHospitalCilinic_Id(id));
    }

    public ServicesEntity findById(Long id){
        return servicesRepository.findById(id).get();
    }

    public void save(Services services) {
        servicesRepository.save(serviceMapper.convertToEntity(services));
    }
}
