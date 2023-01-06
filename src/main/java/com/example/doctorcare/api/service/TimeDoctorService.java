package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.TimeDoctorsMapper;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import com.example.doctorcare.api.domain.dto.request.AddTimeDoctor;
import com.example.doctorcare.api.domain.dto.response.TimeDoctor;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.example.doctorcare.api.exception.TimeDoctorException;
import com.example.doctorcare.api.repository.TimeDoctorRepository;
import com.example.doctorcare.api.utilis.TimeDoctorComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class TimeDoctorService {

    @Autowired
    private TimeDoctorRepository timeDoctorRepository;
    @Autowired
    private TimeDoctorsMapper timeDoctorsMapper;

    public void save(TimeDoctorsEntity timeDoctors) {
        timeDoctorRepository.save(timeDoctors);
    }

    public void saveEntity(TimeDoctorsEntity timeDoctors) {
        timeDoctorRepository.save(timeDoctors);
    }

    public List<TimeDoctors> findAllByDoctor(Long doctorId, LocalDate date) {
        List<TimeDoctors> timeDoctorsList = new ArrayList<>();
        timeDoctorRepository.findByDoctor_IdAndTimeStamp(doctorId).forEach(timeDoctors -> timeDoctorsList.add(timeDoctorsMapper.convertToDto(timeDoctors)));
        if (date != null) {
            timeDoctorsList.removeIf(timeDoctors -> !date.isEqual(timeDoctors.getDate()));
        }
        return timeDoctorsList;
    }

    public void checkExistTimeDoctor(TimeDoctorsEntity timeDoctors) throws TimeDoctorException {
        if (timeDoctorRepository.existsByDateAndTimeEndAndTimeStartAndDoctor_IdAndIdIsNot(
                timeDoctors.getDate(), timeDoctors.getTimeEnd(), timeDoctors.getTimeStart(), timeDoctors.getDoctor().getId(), timeDoctors.getId()))
            throw new TimeDoctorException("Already Exist!");
    }

    public List<TimeDoctors> findByDoctor_IdAndTimeStampAndStatus(Long doctorId, TimeDoctorStatus timeDoctorStatus) {
        List<TimeDoctors> timeDoctorsList = new ArrayList<>();
        timeDoctorRepository.findByDoctor_IdAndTimeStampAndStatus(doctorId, timeDoctorStatus.toString()).stream().sorted(Comparator.comparing(TimeDoctorsEntity::getTimeStart)).forEach(timeDoctors -> timeDoctorsList.add(timeDoctorsMapper.convertToDto(timeDoctors)));
        return timeDoctorsList;
    }


    public TimeDoctorsEntity findById(Long id) {
        return timeDoctorRepository.findById(id).get();
    }

    public TimeDoctorsEntity findByIdEntity(Long id) {
        return timeDoctorRepository.findById(id).get();
    }

/*    public List<TimeDoctors> findFreeTimeByDoctorId(Long id) {
        List<TimeDoctors> timeDoctors = findAllByDoctor(id);
        timeDoctors.forEach(timeDoctors1 -> {
            if (timeDoctors1.getAppointments() != null) timeDoctors.remove(timeDoctors1);
        });
        return timeDoctors;
    }*/

    public void deleteById(Long id) {
        timeDoctorRepository.deleteById(id);
    }

    public List<TimeDoctor> findByDateAndStatus(LocalDate date, Long doctorId) {
        List<TimeDoctor> timeDoctorsList = new ArrayList<>();
        timeDoctorRepository.findByDateAndTimeDoctorStatusAndAndDoctor_Id(date, TimeDoctorStatus.valueOf("AVAILABLE"), doctorId).stream().
                sorted(Comparator.comparing(TimeDoctorsEntity::getTimeStart)).forEach(timeDoctors ->
                {
                    TimeDoctor timeDoctor = new TimeDoctor(timeDoctors.getId(), timeDoctors.getTimeStart().toString(), timeDoctors.getTimeEnd().toString(), timeDoctors.getDate().toString(), timeDoctors.getTimeDoctorStatus().toString());
                    timeDoctorsList.add(timeDoctor);
                });
        return timeDoctorsList;
    }

    public Page<TimeDoctorsEntity> findByDoctorsId(Long id, Pageable pageable, LocalDate before, LocalDate after) {
        LocalDate beforeCreateDate;
        if (before == null) {
            beforeCreateDate = null;
        } else if (before.isBefore(LocalDate.now())) {
            beforeCreateDate = LocalDate.now();
        } else {
            beforeCreateDate = before;
        }
        if (beforeCreateDate == null && after == null) {
            return timeDoctorRepository.findByDoctor_IdAndDateIsAfterAndTimeDoctorStatus(id, LocalDate.now(), TimeDoctorStatus.AVAILABLE, pageable);
        } else if (beforeCreateDate != null && after == null) {
            return timeDoctorRepository.findByDoctor_IdAndDateIsAfterAndTimeDoctorStatus(id, beforeCreateDate, TimeDoctorStatus.AVAILABLE, pageable);
        } else
            return timeDoctorRepository.findByDoctor_IdAndDateBetweenAndTimeDoctorStatus(id, Objects.requireNonNullElseGet(beforeCreateDate, LocalDate::now), after, TimeDoctorStatus.AVAILABLE, pageable);
    }

    public void addTimeDoctor(AddTimeDoctor addTimeDoctor, UserEntity doctor) {
        LocalDate startDate = LocalDate.parse(addTimeDoctor.getCreateDate());
        LocalDate endDate = LocalDate.parse(addTimeDoctor.getEndDate());
        List<Integer> dateOfWeek = Arrays.stream(addTimeDoctor.getDateOfWeek()).map(Integer::parseInt).toList();
        List<TimeDoctorsEntity> timeDoctorsEntities = new ArrayList<>();
        Set<TimeDoctorsEntity> timeDoctorsEntitySet = timeDoctorRepository.findByDoctor_IdAndDateBetween(doctor.getId(), startDate, endDate);
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            TimeDoctorsEntity timeDoctors = new TimeDoctorsEntity(LocalTime.parse(addTimeDoctor.getTimeStart()),
                    LocalTime.parse(addTimeDoctor.getTimeEnd()), date, doctor, TimeDoctorStatus.AVAILABLE);
            if (dateOfWeek.contains((Integer) date.getDayOfWeek().getValue()) && (timeDoctorsEntitySet.isEmpty() || !timeDoctorsEntitySet.contains(timeDoctors))) {
                timeDoctorsEntities.add(timeDoctors);
            }
        }
        timeDoctorRepository.saveAll(timeDoctorsEntities);
    }
}
