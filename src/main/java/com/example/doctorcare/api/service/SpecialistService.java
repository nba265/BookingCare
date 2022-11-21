package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.SpecialistMapper;
import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import com.example.doctorcare.api.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<SpecialistEntity> findById(Long id){
        return specialistRepository.findById(id);
    }

    public void saveEntity(SpecialistEntity specialist){
        specialistRepository.save(specialist);
    }
}
