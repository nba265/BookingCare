/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.repository;


import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmailLikeAndStatusLike(String email,
                                            UserStatus status);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(@NotBlank @Size(max = 20) String username);

    @Query(value = "select u from UserEntity u join u.hospitalCilinicDoctor h where h.id = ?1 ", nativeQuery = false)
    Set<UserEntity> findDoctorByHospitalCilinicId(Long hosId);

    @Query(value = "select u from UserEntity u join u.hospitalCilinicDoctor h where h.id = ?1 and u.specialist.id = ?2 and u.gender = ?3")
    Set<UserEntity> findDoctorByHospitalCilinicIdAndSpecIdAndGender(Long hosId, Long specId, Gender gender);

}
