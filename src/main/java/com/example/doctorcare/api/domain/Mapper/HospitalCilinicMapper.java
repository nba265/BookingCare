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

    @PostConstruct
    public void init(){
        this.userMapper = new UserMapper();
        this.serviceMapper = new ServiceMapper();
    }

    @Override
    public HospitalCilinicEntity convertToEntity(HospitalCilinic dto, Object... args) {
        HospitalCilinicEntity hospitalCilinicEntity = new HospitalCilinicEntity();
        if (dto != null){
            BeanUtils.copyProperties(dto,hospitalCilinicEntity,"doctor","services");
            hospitalCilinicEntity.setDoctor(userMapper.convertToEntitySet(dto.getDoctor()));
            hospitalCilinicEntity.setServices(serviceMapper.convertToEntitySet(dto.getServices()));
        }
        return hospitalCilinicEntity;
    }

    @Override
    public HospitalCilinic convertToDto(HospitalCilinicEntity entity, Object... args) {
        HospitalCilinic hospitalCilinic = new HospitalCilinic();
        if (entity != null){
            BeanUtils.copyProperties(entity,hospitalCilinic,"doctor","services");
            hospitalCilinic.setServices(serviceMapper.convertToDtoList(entity.getServices()));
            hospitalCilinic.setDoctor(userMapper.convertToDtoList(entity.getDoctor()));
        }
        return hospitalCilinic;
    }
}
