package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalCilinicRepository extends CrudRepository<HospitalCilinicEntity,Integer> {
}
