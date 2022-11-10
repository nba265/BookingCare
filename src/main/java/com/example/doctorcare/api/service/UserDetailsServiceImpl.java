/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.service;


import com.example.doctorcare.api.config.security.Services.UserDetailsImpl;
import com.example.doctorcare.api.domain.Mapper.HospitalClinicMapper;
import com.example.doctorcare.api.domain.Mapper.SpecialistMapper;
import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Role;
import com.example.doctorcare.api.enums.UserStatus;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HospitalClinicMapper hospitalClinicMapper;

    @Autowired
    private SpecialistMapper specialistMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public boolean checkUser(String name) {
        return (userRepository.existsByUsername(name) && userRepository.findByUsername(name).get().getUserRoles().stream().anyMatch(userRoleEntity -> userRoleEntity.getRole().toString().equalsIgnoreCase("ROLE_MANAGER")));
    }

    public Iterable<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void save(UserEntity userEntity) {

        userRepository.save(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmailLikeAndStatusLike(email, UserStatus.ACTIVE);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public void save(com.example.doctorcare.api.domain.dto.User user) {
        user.setCreateDate(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(userMapper.convertToEntity(user));
    }

    public List<com.example.doctorcare.api.domain.dto.User> findDoctorByHospitalCilinic(Long hosId) {
        List<com.example.doctorcare.api.domain.dto.User> users = new ArrayList<>();
        userRepository.findDoctorByHospitalCilinicId(hosId).forEach(userEntity -> users.add(userMapper.convertToDto(userEntity)));
        return users;
    }

    public Set<UserEntity> findDoctor(Long hosId, Long specId, String gender, String keyword) {
        Set<UserEntity> users = userRepository.findDoctorByHospitalCilinicId(hosId);
        Set<UserEntity> toRemove = new HashSet<>();
        if (specId != null && specId != 0) {
            users.forEach(user -> {
                if (!Objects.equals(user.getSpecialist().getId(), specId)) {
                    toRemove.add(user);
                }
            });
        }
        if (gender != null && !gender.equals("")) {
            users.forEach(user -> {
                if (!user.getGender().toString().equalsIgnoreCase(gender)) {
                    toRemove.add(user);
                }
            });
        }
        if (keyword != null && !keyword.equals("")) {
            users.forEach(user -> {
                if (!user.getFullName().contains(keyword)) {
                    toRemove.add(user);
                }
            });
        }
        users.removeAll(toRemove);
        return users;
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    public User findDoctorById(Long doctorId) {
        UserEntity userEntity = userRepository.findById(doctorId).get();
        User user = userMapper.convertToDto(userEntity);
        user.setHospitalClinicDoctor(hospitalClinicMapper.convertToDto(userEntity.getHospitalCilinicDoctor()));
        user.setHospitalClinicMangager(hospitalClinicMapper.convertToDto(userEntity.getHospitalCilinicMangager()));
        user.setSpecialist(specialistMapper.convertToDto(userEntity.getSpecialist()));
        return user;
    }

    public User findByTimeDoctorId(Long id) {
        UserEntity userEntity = userRepository.findByTimeDoctors_Id(id);
        User user = userMapper.convertToDto(userEntity);
        user.setHospitalClinicDoctor(hospitalClinicMapper.convertToDto(userEntity.getHospitalCilinicDoctor()));
        user.setHospitalClinicMangager(hospitalClinicMapper.convertToDto(userEntity.getHospitalCilinicMangager()));
        user.setSpecialist(specialistMapper.convertToDto(userEntity.getSpecialist()));
        return user;
    }

    public Page<UserEntity> findUser(String keyword, Long roleId, Pageable pageable) {
        Set<UserRoleEntity> userRoleEntities = new HashSet<>();
        List<UserEntity> userEntities = new ArrayList<>();
        if (roleId == 0 && keyword == null) {
            return userRepository.findAll(pageable);
        }/* else if (roleId != 0){
            userRepository.findByEmailContainsIgnoreCaseOrFullNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrHospitalCilinicDoctor_Name(keyword,roleId)
                    .stream().toList().forEach(user -> {
                        System.out.println(user.getUserRoles().stream().findFirst().get().getId());
                        if (user.getUserRoles().stream().findFirst().get().getId() == roleId) {
                            userEntities.add(user);
                        }
                    });
            return new PageImpl<>(userEntities,pageable,userEntities.size());
        }*/
        else{
            userEntities=userRepository.findByEmailContainsIgnoreCaseOrFullNameContainsIgnoreCaseOrUsernameContainsIgnoreCaseOrHospitalCilinicDoctor_Name(keyword ,roleId);
            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), userEntities.size());
            return new PageImpl<>(userEntities.subList(start, end), pageable, userEntities.size());
        }
    }

}
