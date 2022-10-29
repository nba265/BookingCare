package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ServiceMapper extends BaseMapper<ServicesEntity, Services> {


    @Override
    public ServicesEntity convertToEntity(Services dto, Object... args) {
        ServicesEntity servicesEntity = new ServicesEntity();
        AppointmentMapper appointmentMapper= new AppointmentMapper();
        HospitalCilinicMapper hospitalCilinicMapper = new HospitalCilinicMapper();
        if (dto != null)
            BeanUtils.copyProperties(dto,servicesEntity);
        if(dto!=null){
            servicesEntity.setAppointmentsSet(appointmentMapper.convertToEntitySet(dto.getAppointments()));
        }
        if(dto!=null){
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
