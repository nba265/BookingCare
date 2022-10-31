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
public interface HospitalClinicRepository extends CrudRepository<HospitalClinicEntity,Long> {

    @Query(value = "select h from HospitalClinicEntity h where h.name like %:keyword%")
    Set<HospitalClinicEntity> findByKeywords(@Param("keyword") String keyword);
    HospitalClinicEntity findByManager_Username(String username);

    @Query(value = "select h from HospitalClinicEntity h join h.doctor d join d.timeDoctors t where t.appointments.id = ?1 ")
    HospitalClinicEntity findByAppointment_Id(Long id);

}
