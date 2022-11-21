package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SpecialistRepository extends CrudRepository<SpecialistEntity,Long> {

    @Query("select s from SpecialistEntity s join s.userSet u  where u.hospitalCilinicDoctor.id = ?1")
    Set<SpecialistEntity> findAllByHospitalCilinicId(Long id);

    Iterable<SpecialistEntity> findByHospitalCilinic_Id(Long hospitalCilinic_id);
}
