package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import org.springframework.stereotype.Component;

@Component
public class SpecialistMapper extends BaseMapper<SpecialistEntity, Specialist> {
    @Override
    public SpecialistEntity convertToEntity(Specialist dto, Object... args) {
        return null;
    }

    @Override
    public Specialist convertToDto(SpecialistEntity entity, Object... args) {
        return null;
    }
}
