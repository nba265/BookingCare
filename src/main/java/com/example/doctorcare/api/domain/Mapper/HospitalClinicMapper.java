package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.HospitalClinic;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HospitalClinicMapper extends BaseMapper<HospitalClinicEntity, HospitalClinic> {

    private UserMapper userMapper;

    private ServiceMapper serviceMapper;

    private SpecialistMapper specialistMapper;

    @PostConstruct
    public void init() {
        this.userMapper = new UserMapper();
        this.serviceMapper = new ServiceMapper();
        this.specialistMapper = new SpecialistMapper();
    }

    @Override
    public HospitalClinicEntity convertToEntity(HospitalClinic dto, Object... args) {
        HospitalClinicEntity hospitalClinicEntity = new HospitalClinicEntity();
        userMapper = new UserMapper();
        serviceMapper = new ServiceMapper();
        specialistMapper = new SpecialistMapper();
        if (dto != null) {
            BeanUtils.copyProperties(dto, hospitalClinicEntity, "doctor", "services", "specialists");
            if (dto.getDoctor() != null) {
                hospitalClinicEntity.setDoctor(userMapper.convertToEntitySet(dto.getDoctor()));
            }
            if (dto.getServices() != null) {
                hospitalClinicEntity.setServices(serviceMapper.convertToEntitySet(dto.getServices()));
            }
            if (dto.getSpecialists() != null) {
                hospitalClinicEntity.setSpecialists(specialistMapper.convertToEntitySet(dto.getSpecialists()));
            }
        }
        return hospitalClinicEntity;
    }

    @Override
    public HospitalClinic convertToDto(HospitalClinicEntity entity, Object... args) {
        HospitalClinic hospitalClinic = new HospitalClinic();
        userMapper = new UserMapper();
        serviceMapper = new ServiceMapper();
        specialistMapper = new SpecialistMapper();
        if (entity != null) {
            BeanUtils.copyProperties(entity, hospitalClinic, "doctor", "services", "specialists");
            if (entity.getServices() != null && !entity.getServices().isEmpty()) {
                hospitalClinic.setServices(serviceMapper.convertToDtoList(entity.getServices()));
            }
            if (entity.getDoctor() != null && !entity.getDoctor().isEmpty()) {
                hospitalClinic.setDoctor(userMapper.convertToDtoList(entity.getDoctor()));
            }
            if (entity.getSpecialists() != null && !entity.getSpecialists().isEmpty()) {
                hospitalClinic.setSpecialists(specialistMapper.convertToDtoList(entity.getSpecialists()));
            }
        }
        return hospitalClinic;
    }
}
