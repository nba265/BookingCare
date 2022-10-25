package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpecialistMapper extends BaseMapper<SpecialistEntity, Specialist> {

    private UserMapper userMapper;

    @PostConstruct
    public void init(){
        this.userMapper = new UserMapper();
    }
    @Override
    public SpecialistEntity convertToEntity(Specialist dto, Object... args) {
        return null;
    }

    @Override
    public Specialist convertToDto(SpecialistEntity entity, Object... args) {
        Specialist specialist = new Specialist();
        if (entity != null){
            BeanUtils.copyProperties(entity,specialist,"userSet");
            specialist.setUsers(userMapper.convertToDtoList(entity.getUserSet()));
        }
        return specialist;
    }
}
