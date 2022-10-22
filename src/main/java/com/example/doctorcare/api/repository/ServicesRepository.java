package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.entity.ServicesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends CrudRepository<ServicesEntity,Integer> {
}
