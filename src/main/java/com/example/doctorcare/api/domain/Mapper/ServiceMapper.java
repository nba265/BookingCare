package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ServiceMapper extends BaseMapper<ServicesEntity, Services> {


    @Override
    public ServicesEntity convertToEntity(Services dto, Object... args) {

        ServicesEntity servicesEntity = new ServicesEntity();
        AppointmentMapper appointmentMapper = new AppointmentMapper();
        if (dto != null)
            BeanUtils.copyProperties(dto, servicesEntity,"appointments");
        assert dto != null;
        if (dto.getAppointments() != null && !dto.getAppointments().isEmpty()) {
            servicesEntity.setAppointmentsSet((new HashSet<>(dto.getAppointments())));
        }
        return servicesEntity;
    }

    @Override
    public Services convertToDto(ServicesEntity entity, Object... args) {
        Services services = new Services();
        AppointmentMapper appointmentMapper = new AppointmentMapper();
        if (entity != null)
            BeanUtils.copyProperties(entity, services);
        assert entity != null;
        if (entity.getAppointmentsSet() != null && !entity.getAppointmentsSet().isEmpty()) {
            services.setAppointments((new ArrayList<>(entity.getAppointmentsSet())));
        }
        return services;
    }
}
