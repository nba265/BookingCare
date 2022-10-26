package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HospitalCilinicMapper extends BaseMapper<HospitalCilinicEntity, HospitalCilinic> {

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
    public HospitalCilinicEntity convertToEntity(HospitalCilinic dto, Object... args) {
        HospitalCilinicEntity hospitalCilinicEntity = new HospitalCilinicEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, hospitalCilinicEntity, "doctor", "services", "specialists");
            if (dto.getDoctor() != null) {
                hospitalCilinicEntity.setDoctor(userMapper.convertToEntitySet(dto.getDoctor()));
            }
            if (dto.getServices() != null) {
                hospitalCilinicEntity.setServices(serviceMapper.convertToEntitySet(dto.getServices()));
            }
            if (dto.getSpecialists() != null) {
                hospitalCilinicEntity.setSpecialists(specialistMapper.convertToEntitySet(dto.getSpecialists()));
            }
        }
        return hospitalCilinicEntity;
    }

    @Override
    public HospitalCilinic convertToDto(HospitalCilinicEntity entity, Object... args) {
        HospitalCilinic hospitalCilinic = new HospitalCilinic();
        if (entity != null) {
            BeanUtils.copyProperties(entity, hospitalCilinic, "doctor", "services", "specialists");
            if(entity.getServices() != null && !entity.getServices().isEmpty())
            hospitalCilinic.setServices(serviceMapper.convertToDtoList(entity.getServices()));
            if(entity.getDoctor() != null && !entity.getDoctor().isEmpty())
            hospitalCilinic.setDoctor(userMapper.convertToDtoList(entity.getDoctor()));
            if (entity.getSpecialists() != null && !entity.getSpecialists().isEmpty())
            hospitalCilinic.setSpecialists(specialistMapper.convertToDtoList(entity.getSpecialists()));
        }
        return hospitalCilinic;
    }
}
