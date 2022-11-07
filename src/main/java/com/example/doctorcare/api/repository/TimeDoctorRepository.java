package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.enums.TimeDoctorStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TimeDoctorRepository extends CrudRepository<TimeDoctorsEntity, Long> {
    @Query(value = "select * from time_doctors td where TIMESTAMP(`date`, time_start) > timestamp(curdate(),current_time()) and td.doctor_id=?1", nativeQuery = true)
    Set<TimeDoctorsEntity> findByDoctor_IdAndTimeStamp(Long doctorId);

    @Query(value = "select t from TimeDoctorsEntity t where t.doctor.id = ?1 and t.timeDoctorStatus = ?2")
    Set<TimeDoctorsEntity> findByDoctor_IdAndTimeDoctorStatus(Long doctor_id, TimeDoctorStatus timeDoctorStatus);

    Optional<TimeDoctorsEntity> findById(Long id);

    void deleteById(Long id);

}
