package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.ServicesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ServicesRepository extends CrudRepository<ServicesEntity,Integer> {

    Set<ServicesEntity> findAllByHospitalCilinic_Id(Long hospitalCilinic_id);

    Optional<ServicesEntity> findById(Long id);
}
