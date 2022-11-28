package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface HospitalClinicRepository extends CrudRepository<HospitalClinicEntity, Long> {

    @Query(value = "select h from HospitalClinicEntity h where h.name like %?1% and h.districtCode = ?2 ")
    Set<HospitalClinicEntity> findByKeywordsAndDistrictCode(String keyword, String code);

    @Query(value = "select h from HospitalClinicEntity h where h.name like %?1% or h.districtCode = ?2 ")
    Set<HospitalClinicEntity> findByKeywordsOrDistrictCode(String keyword, String code);

    @Query(value = "select h from HospitalClinicEntity h where h.districtCode = ?1 ")
    Set<HospitalClinicEntity> findByDistrictCode(String code);

    @Query(value = "select h from HospitalClinicEntity h where h.name like %?1%")
    Set<HospitalClinicEntity> findByKeywords(String keyword);

    HospitalClinicEntity findByManager_Username(String username);

    @Query(value = "select h from HospitalClinicEntity h join h.doctor d join d.timeDoctors t where t.appointments.id = ?1 ")
    HospitalClinicEntity findByAppointment_Id(Long id);

    @Query(value = "select h.* from hospital_cilinic h join `user` u on h.id = u.hospital_cilinic_id " +
            "join time_doctors td on td.doctor_id = u.id " +
            "join appointments a on a.cancel_time_doctors_id = td.id " +
            "where a.id = ?1", nativeQuery = true)
    HospitalClinicEntity findByCancelAppointment_Id(Long id);

}
