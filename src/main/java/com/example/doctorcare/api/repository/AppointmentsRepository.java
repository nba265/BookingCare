package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AppointmentsRepository extends CrudRepository<AppointmentsEntity, Long> {
    @Query(value = "select a.* from appointments a join time_doctors td on a.id=td.appointments_id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1",nativeQuery = true)
    Set<AppointmentsEntity> findByHostpital(Long id);
}
