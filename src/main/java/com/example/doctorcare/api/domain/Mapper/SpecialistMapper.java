package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            BeanUtils.copyProperties(dto,specialistEntity,"userSet");
            if (dto.getUsers() != null && !dto.getUsers().isEmpty()) {
                specialistEntity.setUserSet(userMapper.convertToEntitySet(dto.getUsers()));
            }
        }
        return specialistEntity;
    }

    @Override
    public Specialist convertToDto(SpecialistEntity entity, Object... args) {
        Specialist specialist = new Specialist();
        userMapper = new UserMapper();
        if (entity != null){
            BeanUtils.copyProperties(entity,specialist,"userSet");
            if (entity.getUserSet() != null && !entity.getUserSet().isEmpty()) {
                specialist.setUsers(userMapper.convertToDtoList(entity.getUserSet()));
            }
        }
        return specialist;
    }
}
