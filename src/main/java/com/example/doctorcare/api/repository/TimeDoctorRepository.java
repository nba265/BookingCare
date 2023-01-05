package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TimeDoctorRepository extends CrudRepository<TimeDoctorsEntity, Long> {
    @Query(value = "select * from time_doctors td where TIMESTAMP(`date`, time_start) > timestamp(curdate(),current_time()) and td.doctor_id=?1", nativeQuery = true)
    Set<TimeDoctorsEntity> findByDoctor_IdAndTimeStamp(Long doctorId);

    @Query(value = "select t from TimeDoctorsEntity t where t.doctor.id = ?1 and t.timeDoctorStatus = ?2")
    Set<TimeDoctorsEntity> findByDoctor_IdAndTimeDoctorStatus(Long doctor_id, TimeDoctorStatus timeDoctorStatus);

    @Query(value = "select * from time_doctors td where TIMESTAMP(`date`, time_start) > timestamp(curdate(),current_time()) and td.doctor_id=?1 and td.time_doctor_status = ?2", nativeQuery = true)
    Set<TimeDoctorsEntity> findByDoctor_IdAndTimeStampAndStatus(Long doctorId,String timeDoctorStatus);

    Optional<TimeDoctorsEntity> findById(Long id);
    List<TimeDoctorsEntity> findByDateAndTimeDoctorStatusAndAndDoctor_Id(LocalDate date, TimeDoctorStatus status,Long doctorId);
    Page<TimeDoctorsEntity> findByDoctor_IdAndDateIsBeforeAndTimeDoctorStatus(Long doctor_id, LocalDate date, TimeDoctorStatus timeDoctorStatus, Pageable pageable);


    Page<TimeDoctorsEntity> findByDoctor_IdAndDateIsAfterAndTimeDoctorStatus(Long doctor_id, LocalDate date, TimeDoctorStatus timeDoctorStatus, Pageable pageable);

    Page<TimeDoctorsEntity> findByDoctor_IdAndDateBetweenAndTimeDoctorStatus(Long doctor_id, LocalDate date,  LocalDate date2,TimeDoctorStatus timeDoctorStatus, Pageable pageable);

    Set<TimeDoctorsEntity> findByDoctor_IdAndDateBetween(Long doctor_id, LocalDate date, LocalDate date2);


    @Query("""
            select (count(t) > 0) from TimeDoctorsEntity t
            where t.date = ?1  and t.doctor.id = ?4 and
            ((?3 <= t.timeStart and ?2 >= t.timeStart)
            or (?2 >= t.timeEnd and ?3 <= t.timeEnd)
            or (?2 >= t.timeStart and ?3 <= t.timeEnd))""")
    Boolean existsByDateAndTimeEndAndTimeStartAndDoctor_Id(LocalDate date, LocalTime timeEnd, LocalTime timeStart, Long doctor_id);

    void deleteById(Long id);

}
