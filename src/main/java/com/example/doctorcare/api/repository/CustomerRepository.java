package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.CustomersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomersEntity,Integer> {
}
