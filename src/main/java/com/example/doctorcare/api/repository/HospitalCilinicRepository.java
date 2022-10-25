package com.example.doctorcare.api.repository;

import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface HospitalCilinicRepository extends CrudRepository<HospitalCilinicEntity,Integer> {

    @Query(value = "select h from HospitalCilinicEntity h where h.name like %:keyword%")
    Set<HospitalCilinicEntity> findByKeywords(@Param("keyword") String keyword);
}
