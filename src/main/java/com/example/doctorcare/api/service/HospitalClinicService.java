package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.HospitalClinicMapper;
import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.HospitalClinic;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.repository.HospitalClinicRepository;
import com.example.doctorcare.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalClinicService {

    @Autowired
    private HospitalClinicRepository hospitalClinicRepository;
    
    @Autowired
    private HospitalClinicMapper hospitalClinicMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<HospitalClinic> hospitalCilinicList(){
        List<HospitalClinic> hospitalClinics = new ArrayList<>();
        hospitalClinicRepository.findAll().forEach(hospitalCilinicEntity -> hospitalClinics.add(hospitalClinicMapper.convertToDto(hospitalCilinicEntity)));
        return hospitalClinics;
    }

    public List<HospitalClinic> findByKeywords(String keyword){
        List<HospitalClinic> hospitalClinics = new ArrayList<>();
        hospitalClinicRepository.findByKeywords(keyword).forEach(hospitalCilinicEntity -> hospitalClinics.add(hospitalClinicMapper.convertToDto(hospitalCilinicEntity)));
        return hospitalClinics;
    }

    public void save(HospitalClinic hospitalClinic){
        hospitalClinicRepository.save(hospitalClinicMapper.convertToEntity(hospitalClinic));
    }
    public void save(HospitalClinicEntity hospitalClinicEntity){
        hospitalClinicRepository.save(hospitalClinicEntity);
    }
    public HospitalClinicEntity findByManagerUsername(String username){
        return hospitalClinicRepository.findByManager_Username(username);

    }

    public HospitalClinicEntity findById(Long id){
        return hospitalClinicRepository.findById(id).get();
    }

    public HospitalClinic findByDoctorId(Long docId){
        return hospitalClinicMapper.convertToDto(userRepository.findById(docId).get().getHospitalCilinicDoctor());
    }

    public HospitalClinic findByAppointment_Id(Long id){
        return hospitalClinicMapper.convertToDto(hospitalClinicRepository.findByAppointment_Id(id));
    }

}
