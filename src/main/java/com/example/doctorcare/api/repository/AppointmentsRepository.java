package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.AppointmentsEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentsRepository extends CrudRepository<AppointmentsEntity, Long> {
    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1 order by a.id", nativeQuery = true)
    //Page<AppointmentsEntity> findByHospital(Long id, Pageable pageable);
    Page<AppointmentsEntity> findByServices_HospitalCilinic_Id(Long id, Pageable pageable);
//    @Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where u.id=?1 order by a.id", nativeQuery = true)
    Page<AppointmentsEntity> findByTimeDoctors_Doctor_Id(Long id, Pageable pageable);

    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1 and date(?2) >= td.date", nativeQuery = true)
    Page<AppointmentsEntity> findByServices_HospitalCilinic_IdAndCreateDateAfter(Long id, LocalDateTime before,
                                                              Pageable pageable);

    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1 and date(?2) <= td.date", nativeQuery = true)
    Page<AppointmentsEntity> findByUser_IdAndCreateDateBefore(Long id, LocalDateTime after,
                                                               Pageable pageable);
    Page<AppointmentsEntity> findByUser_Id(Long id, Pageable pageable);

    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1 and date(?2) >= td.date", nativeQuery = true)
    Page<AppointmentsEntity> findByUser_IdAndCreateDateAfter(Long id, LocalDateTime before,
                                                                                 Pageable pageable);

    Page<AppointmentsEntity> findByUser_IdAndCreateDateBetween(Long id, LocalDateTime before, LocalDateTime after, Pageable pageable);


    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1 and date(?2) <= td.date", nativeQuery = true)
    Page<AppointmentsEntity> findByServices_HospitalCilinic_IdAndCreateDateBefore(Long id, LocalDateTime after,
                                                                                  Pageable pageable);


    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where u.id = ?1 and date(?2) >= td.date ", nativeQuery = true)
    Page<AppointmentsEntity> findByTimeDoctors_Doctor_IdAndCreateDateAfter(Long id, LocalDateTime before,
                                                              Pageable pageable);

    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where u.id = ?1 and date(?2) <= td.date ", nativeQuery = true)
    Page<AppointmentsEntity> findByTimeDoctors_Doctor_IdAndCreateDateBefore(Long id, LocalDateTime after,
                                                               Pageable pageable);

    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where u.id = ?1 and date(?3) <= td.date and date(2) >= td.date ", nativeQuery = true)
    Page<AppointmentsEntity> findByTimeDoctors_Doctor_IdAndCreateDateBetween(Long id, LocalDateTime before, LocalDateTime after, Pageable pageable);

    //@Query(value = "select a.* from appointments a join time_doctors td on a.time_doctors_id=td.id join `user` u on td.doctor_id= u.id where hospital_cilinic_id=?1 and  (td.date between date(?2) and date(?3)) ", nativeQuery = true)
    Page<AppointmentsEntity> findByServices_HospitalCilinic_IdAndCreateDateBetween(Long id, LocalDateTime before, LocalDateTime after, Pageable pageable);

    /*Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCase(String bookName,
                                                                     Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCaseAndCreateDateAfter(String bookName, LocalDateTime before,Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCaseAndCreateDateBefore(String bookName, LocalDateTime after, Pageable pageable);

    Page<AppointmentsEntity> findByUser_FullNameContainingIgnoreCaseAndCreateDateBetween(String bookName, LocalDateTime before, LocalDateTime after, Pageable pageable);
*/
    AppointmentsEntity findByTimeDoctors_Id(Long timeDoctors_id);

    Optional<AppointmentsEntity> findByAppointmentCode(String appointmentCode);
}
