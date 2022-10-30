package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper extends BaseMapper<ServicesEntity, Services> {


    @Override
    public ServicesEntity convertToEntity(Services dto, Object... args) {

        ServicesEntity servicesEntity = new ServicesEntity();
        AppointmentMapper appointmentMapper = new AppointmentMapper();
        if (dto != null)
            BeanUtils.copyProperties(dto, servicesEntity,"appointments");
        if (dto.getAppointments() != null && !dto.getAppointments().isEmpty()) {
            servicesEntity.setAppointmentsSet(appointmentMapper.convertToEntitySet(dto.getAppointments()));
        }
        return servicesEntity;
    }

    @Override
    public Services convertToDto(ServicesEntity entity, Object... args) {
        Services services = new Services();
        AppointmentMapper appointmentMapper = new AppointmentMapper();
        if (entity != null)
            BeanUtils.copyProperties(entity, services);
        if (entity.getAppointmentsSet() != null && !entity.getAppointmentsSet().isEmpty()) {
            services.setAppointments(appointmentMapper.convertToDtoList(entity.getAppointmentsSet()));
        }
        return services;
    }
}
