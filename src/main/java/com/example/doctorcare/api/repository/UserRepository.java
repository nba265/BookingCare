/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.repository;


import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmailLikeAndStatusLike(String email,
                                            UserStatus status);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select u from UserEntity u join u.timeDoctors t where t.id = ?1")
    UserEntity findByTimeDoctors_Id(Long id);

    Optional<UserEntity> findByUsername(@NotBlank @Size(max = 20) String username);

    @Query(value = "select u from UserEntity u join u.hospitalCilinicDoctor h where h.id = ?1 ", nativeQuery = false)
    Set<UserEntity> findDoctorByHospitalCilinicId(Long hosId);

    @Query(value = "select u from UserEntity u join u.hospitalCilinicDoctor h where h.id = ?1 and u.specialist.id = ?2 and u.gender = ?3")
    Set<UserEntity> findDoctorByHospitalCilinicIdAndSpecIdAndGender(Long hosId, Long specId, Gender gender);

    Page<UserEntity> findAll(Pageable pageable);

    /*
        Page<UserEntity> findByEmailContainsIgnoreCaseOrFullNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrHospitalCilinicDoctor_Name(String email, String fullName, @NotBlank @Size(max = 20) String username, String hospitalCilinicDoctor_name, Pageable pageable);
    */
    @Query(value = "select u.* from user u join user_role_relationship urr " +
            "on u.id = urr.user_id join user_role ur on urr.role_id = ur.id " +
            "left join hospital_cilinic hc on u.hospital_cilinic_id = hc.id " +
            "where (UPPER(u.username) like concat('%',UPPER(?1),'%') or UPPER(full_name) like concat('%',UPPER(?1),'%') or UPPER(email) like concat('%',UPPER(?1),'%') or UPPER(hc.name) like concat('%',UPPER(?1),'%')) and ur.id = ?2", nativeQuery = true)
    List<UserEntity> findUserByKeywordAndRole_Id(String keyword, Long roleId);

    @Query(value = "select u.* from user u join user_role_relationship urr " +
            "on u.id = urr.user_id join user_role ur on urr.role_id = ur.id " +
            "left join hospital_cilinic hc on u.hospital_cilinic_id = hc.id " +
            "where UPPER(u.username) like concat('%',UPPER(?1),'%') or UPPER(full_name) like concat('%',UPPER(?1),'%') or UPPER(email) like concat('%',UPPER(?1),'%') or UPPER(hc.name) like concat('%',UPPER(?1),'%') or ur.id = ?2", nativeQuery = true)
    List<UserEntity> findUserByKeywordOrRole_Id(String keyword, Long roleId);

    @Query(value = "select u.* from user u join user_role_relationship urr " +
            "on u.id = urr.user_id join user_role ur on urr.role_id = ur.id " +
            "where ur.id = ?1", nativeQuery = true)
    List<UserEntity> findByRole_Id(Long roleId);


}
