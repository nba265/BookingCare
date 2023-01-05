package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.HospitalClinicMapper;
import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.HospitalClinic;
import com.example.doctorcare.api.domain.dto.response.HospitalClinicInfoResponse;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.repository.HospitalClinicRepository;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.utilis.DistrictCodeConst;
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

    public List<HospitalClinicInfoResponse> hospitalCilinicList() {
        List<HospitalClinicInfoResponse> hospitalClinics = new ArrayList<>();
        hospitalClinicRepository.findAll().forEach(hospitalCilinicEntity -> hospitalClinics.add(new
                HospitalClinicInfoResponse(hospitalCilinicEntity.getId(), hospitalCilinicEntity.getName(), hospitalCilinicEntity.getAddress(),
                hospitalCilinicEntity.getPhone(), hospitalCilinicEntity.getManager().getUsername(),DistrictCodeConst.district.get(hospitalCilinicEntity.getDistrictCode()))));
        return hospitalClinics;
    }

    public List<HospitalClinic> findByKeywordsOrDistrictCode(String keyword, String code) {
        List<HospitalClinic> hospitalClinics = new ArrayList<>();
        if (keyword.equals("") && code.equals("")) {
            hospitalClinicRepository.findAll().forEach(hospitalCilinicEntity -> hospitalClinics.add(hospitalClinicMapper.convertToDto(hospitalCilinicEntity)));
        } else if (!keyword.equals("") && !code.equals("")) {
            hospitalClinicRepository.findByKeywordsAndDistrictCode(keyword, code).forEach(hospitalCilinicEntity -> hospitalClinics.add(hospitalClinicMapper.convertToDto(hospitalCilinicEntity)));
        } else if (keyword.equals("")) {
            hospitalClinicRepository.findByDistrictCode(code).forEach(hospitalCilinicEntity -> hospitalClinics.add(hospitalClinicMapper.convertToDto(hospitalCilinicEntity)));
        } else {
            hospitalClinicRepository.findByKeywords(keyword).forEach(hospitalCilinicEntity -> hospitalClinics.add(hospitalClinicMapper.convertToDto(hospitalCilinicEntity)));
        }
        return hospitalClinics;
    }

    public void save(HospitalClinic hospitalClinic) {
        hospitalClinicRepository.save(hospitalClinicMapper.convertToEntity(hospitalClinic));
    }

    public void save(HospitalClinicEntity hospitalClinicEntity) {
        hospitalClinicRepository.save(hospitalClinicEntity);
    }

    public HospitalClinicEntity findByManagerUsername(String username) {
        return hospitalClinicRepository.findByManager_Username(username);
    }

    public HospitalClinicEntity findById(Long id) {
        return hospitalClinicRepository.findById(id).get();
    }

    public HospitalClinic findByDoctorId(Long docId) {
        return hospitalClinicMapper.convertToDto(userRepository.findById(docId).get().getHospitalCilinicDoctor());
    }

    public HospitalClinicEntity findByAppointment_Id(Long id, AppointmentStatus status) {
        if (status.equals(AppointmentStatus.CANCEL))
            return hospitalClinicRepository.findByCancelAppointment_Id(id);
        else
            return hospitalClinicRepository.findByAppointment_Id(id);
    }

}
