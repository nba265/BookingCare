package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpecialistMapper extends BaseMapper<SpecialistEntity, Specialist> {

    private UserMapper userMapper;

    private HospitalCilinicMapper hospitalCilinicMapper;

    @PostConstruct
    public void init(){
        this.userMapper = new UserMapper();
        this.hospitalCilinicMapper = new HospitalCilinicMapper();
    }
    @Override
    public SpecialistEntity convertToEntity(Specialist dto, Object... args) {
        SpecialistEntity specialistEntity = new SpecialistEntity();
        if (dto != null){
            BeanUtils.copyProperties(dto,specialistEntity,"userSet","hospitalCilinic");
            specialistEntity.setUserSet(userMapper.convertToEntitySet(dto.getUsers()));
            specialistEntity.setHospitalCilinic(hospitalCilinicMapper.convertToEntity(dto.getHospitalCilinic()));
        }
        return specialistEntity;
    }

    @Override
    public Specialist convertToDto(SpecialistEntity entity, Object... args) {
        Specialist specialist = new Specialist();
        if (entity != null){
            BeanUtils.copyProperties(entity,specialist,"userSet","hospitalCilinic");
            specialist.setUsers(userMapper.convertToDtoList(entity.getUserSet()));
            specialist.setHospitalCilinic(hospitalCilinicMapper.convertToDto(entity.getHospitalCilinic()));
        }
        return specialist;
    }
}
