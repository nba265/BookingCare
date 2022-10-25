package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.SpecialistMapper;
import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialistService {

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private SpecialistMapper specialistMapper;

    public List<Specialist> findAllByHospitalCilinicId(Long id){
        List<Specialist> specialists = new ArrayList<>();
        specialistRepository.findAllByHospitalCilinicId(id).forEach(specialistEntity -> specialists.add(specialistMapper.convertToDto(specialistEntity)));
        return specialists;
    }
}
