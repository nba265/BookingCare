package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface AppointmentsRepository extends CrudRepository<AppointmentsEntity, Long> {
    @Query(value = "select a.* from appointments a join time_doctors td on a.id=td.appointments_id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1",nativeQuery = true)
    Page<AppointmentsEntity> findByHostpital(Long id,
                                             Pageable pageable);

    Page<AppointmentsEntity> findByCreateDateAfter(LocalDateTime before,
                                                 Pageable pageable);

    Page<AppointmentsEntity> findByCreateDateBefore(LocalDateTime after,
                                                  Pageable pageable);

    Page<AppointmentsEntity> findByCreateDateBetween(LocalDateTime before, LocalDateTime after,Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCase(String bookName,
                                                                     Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCaseAndCreateDateAfter(String bookName, LocalDateTime before,Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCaseAndCreateDateBefore(String bookName, LocalDateTime after, Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCaseAndCreateDateBetween(String bookName, LocalDateTime before, LocalDateTime after, Pageable pageable);
}
