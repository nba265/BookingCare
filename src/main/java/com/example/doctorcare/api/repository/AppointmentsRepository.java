package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentsRepository extends CrudRepository<AppointmentsEntity,Long> {
}
