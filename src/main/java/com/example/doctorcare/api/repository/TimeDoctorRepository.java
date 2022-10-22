package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.entity.TimeDoctorsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeDoctorRepository extends CrudRepository<TimeDoctorsEntity,Integer> {
}
