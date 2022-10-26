package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TimeDoctorsMapper extends BaseMapper<TimeDoctorsEntity, TimeDoctors> {

    @Override
    public TimeDoctorsEntity convertToEntity(TimeDoctors dto, Object... args) {
        TimeDoctorsEntity timeDoctorsEntity = new TimeDoctorsEntity();
        BeanUtils.copyProperties(dto,timeDoctorsEntity);
        return timeDoctorsEntity;
    }

    @Override
    public TimeDoctors convertToDto(TimeDoctorsEntity entity, Object... args) {
        TimeDoctors timeDoctors = new TimeDoctors();
        BeanUtils.copyProperties(entity,timeDoctors);
        return timeDoctors;
    }
}
