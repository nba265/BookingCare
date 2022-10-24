package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.HospitalCilinicMapper;
import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.repository.HospitalCilinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalCilinicService {

    @Autowired
    private HospitalCilinicRepository hospitalCilinicRepository;
    
    @Autowired
    private HospitalCilinicMapper hospitalCilinicMapper;

    public List<HospitalCilinic> hospitalCilinicList(){
        List<HospitalCilinic> hospitalCilinics = new ArrayList<>();
        hospitalCilinicRepository.findAll().forEach(hospitalCilinicEntity -> hospitalCilinics.add(hospitalCilinicMapper.convertToDto(hospitalCilinicEntity)));
        return hospitalCilinics;
    }
}
