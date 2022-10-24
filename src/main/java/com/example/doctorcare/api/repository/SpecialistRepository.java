package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends CrudRepository<SpecialistEntity,Integer> {
}
