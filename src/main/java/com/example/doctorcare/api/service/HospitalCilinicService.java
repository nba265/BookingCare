package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.HospitalCilinicMapper;
import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.repository.HospitalCilinicRepository;
import com.example.doctorcare.api.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<HospitalCilinic> hospitalCilinicList(){
        List<HospitalCilinic> hospitalCilinics = new ArrayList<>();
        hospitalCilinicRepository.findAll().forEach(hospitalCilinicEntity -> hospitalCilinics.add(hospitalCilinicMapper.convertToDto(hospitalCilinicEntity)));
        return hospitalCilinics;
    }

    public List<User> findAllDoctorInHospitalCilinic(Long id){
        List<User> users = new ArrayList<>();
        userRepository.findByHospitalCilinicId(id).forEach(userEntity -> users.add(userMapper.convertToDto(userEntity)));
        return users;
    }
}
