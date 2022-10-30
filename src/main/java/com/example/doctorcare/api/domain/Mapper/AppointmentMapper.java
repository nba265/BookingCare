package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Appointment;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper extends  BaseMapper<AppointmentsEntity, Appointment> {

    ServiceMapper serviceMapper;

    CustomerMapper customerMapper;

    TimeDoctorsMapper timeDoctorsMapper;

    UserMapper userMapper;
    @Override
    public AppointmentsEntity convertToEntity(Appointment dto, Object... args) {
        serviceMapper = new ServiceMapper();
        customerMapper = new CustomerMapper();
        timeDoctorsMapper = new TimeDoctorsMapper();
        userMapper = new UserMapper();
        AppointmentsEntity appointments = new AppointmentsEntity();
        if (dto != null){
            BeanUtils.copyProperties(dto,appointments,"services","customers","timeDoctors","user");
        }
        assert dto != null;
        if (dto.getServices() != null)
            appointments.setServices(serviceMapper.convertToEntity(dto.getServices()));
        if (dto.getTimeDoctors() != null)
            appointments.setTimeDoctors(timeDoctorsMapper.convertToEntity(dto.getTimeDoctors()));
        if (dto.getCustomer() != null)
            appointments.setCustomers(customerMapper.convertToEntity(dto.getCustomer()));
        appointments.setUser(userMapper.convertToEntity(dto.getUser()));
        return appointments ;
    }

    @Override
    public Appointment convertToDto(AppointmentsEntity entity, Object... args) {
        serviceMapper = new ServiceMapper();
        customerMapper = new CustomerMapper();
        timeDoctorsMapper = new TimeDoctorsMapper();
        userMapper = new UserMapper();
        Appointment appointments = new Appointment();
        if (entity != null){
            BeanUtils.copyProperties(entity,appointments,"services","customers","timeDoctors","user");
        }
        assert entity != null;
        if (entity.getServices() != null)
            appointments.setServices(serviceMapper.convertToDto(entity.getServices()));
        if (entity.getTimeDoctors() != null)
            appointments.setTimeDoctors(timeDoctorsMapper.convertToDto(entity.getTimeDoctors()));
        if (entity.getCustomers() != null)
            appointments.setCustomer(customerMapper.convertToDto(entity.getCustomers()));
        appointments.setUser(userMapper.convertToDto(entity.getUser()));
        return appointments ;
    }
}
