package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.response.TimeDoctor;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeDoctorsMapper extends BaseMapper<TimeDoctorsEntity, TimeDoctors> {

    @Override
    public TimeDoctorsEntity convertToEntity(TimeDoctors dto, Object... args) {
        TimeDoctorsEntity timeDoctorsEntity = new TimeDoctorsEntity();
        UserMapper userMapper = new UserMapper();
        AppointmentMapper appointmentMapper = new AppointmentMapper();
        BeanUtils.copyProperties(dto, timeDoctorsEntity);
        timeDoctorsEntity.setDoctor(userMapper.convertToEntity(dto.getDoctor()));
        timeDoctorsEntity.setAppointments(appointmentMapper.convertToEntity(dto.getAppointments()));
        return timeDoctorsEntity;
    }

    @Override
    public TimeDoctors convertToDto(TimeDoctorsEntity entity, Object... args) {
        TimeDoctors timeDoctors = new TimeDoctors();
        BeanUtils.copyProperties(entity, timeDoctors);
        return timeDoctors;
    }

    public TimeDoctor convertToResponse(TimeDoctors entity, Object... args) {
        TimeDoctor timeDoctor = new TimeDoctor();
        timeDoctor.setId(entity.getId());
        timeDoctor.setTimeStart(entity.getTimeStart().toString());
        timeDoctor.setTimeEnd(entity.getTimeEnd().toString());
        return timeDoctor;
    }

    @Override
    public List<TimeDoctors> convertToDtoList(Collection<TimeDoctorsEntity> entities, Object... args) {
        return super.convertToDtoList(entities, args);
    }

    public List<TimeDoctor> convertToResponseList(Collection<TimeDoctors> entities, Object... args) {
        return entities.stream().map(timeDoctors1 -> convertToResponse(timeDoctors1, args)).toList();
    }
}
