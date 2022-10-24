package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper extends BaseMapper<ServicesEntity, Services> {
    @Override
    public ServicesEntity convertToEntity(Services dto, Object... args) {
        return null;
    }

    @Override
    public Services convertToDto(ServicesEntity entity, Object... args) {
        return null;
    }
}
