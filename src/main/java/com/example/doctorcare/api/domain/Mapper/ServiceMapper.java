package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper extends BaseMapper<ServicesEntity, Services> {


    @Override
    public ServicesEntity convertToEntity(Services dto, Object... args) {
        HospitalCilinicMapper hospitalCilinicMapper= new HospitalCilinicMapper();
        ServicesEntity servicesEntity = new ServicesEntity();
        if (dto != null){
            BeanUtils.copyProperties(dto,servicesEntity);
            servicesEntity.setHospitalCilinic(hospitalCilinicMapper.convertToEntity(dto.getHospitalCilinic()));
        }
        return servicesEntity;
    }

    @Override
    public Services convertToDto(ServicesEntity entity, Object... args) {
        Services services = new Services();
        if (entity != null)
            BeanUtils.copyProperties(entity,services);
        return services;
    }
}
