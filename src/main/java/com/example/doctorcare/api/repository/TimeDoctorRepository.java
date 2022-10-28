package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TimeDoctorRepository extends CrudRepository<TimeDoctorsEntity,Integer> {
    Set<TimeDoctorsEntity> findByDoctor_Id(Long doctorId);
    Optional<TimeDoctorsEntity> findById(Long id);

}
