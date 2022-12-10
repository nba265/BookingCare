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
import com.example.doctorcare.api.domain.dto.request.AddDoctor;
import com.example.doctorcare.api.domain.dto.request.EditDoctor;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.Role;
import com.example.doctorcare.api.enums.UserStatus;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.repository.UserRoleRepository;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private HospitalClinicService hospitalClinicService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public boolean checkUser(String name) {
        return (userRepository.existsByUsername(name) && userRepository.findByUsername(name).get().getHospitalCilinicMangager() == null && userRepository.findByUsername(name).get().getUserRoles().stream().anyMatch(userRoleEntity -> userRoleEntity.getRole().toString().equalsIgnoreCase("ROLE_MANAGER")));
    }

    public boolean checkExistsUsername(String username) {
        return userRepository.existsByUsername(username.trim());
    }

    public boolean checkExistsEmail(String email) {
        return userRepository.existsByEmail(email.trim());
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
       /* if ((hosId == 0) && (specId == 0) && (gender.equals("")) && (keyword.equals("")))
            return new HashSet<>(userRepository.findByRole_Id(3L));
        else*/ return new HashSet<>(userRepository.findDoctorByHospitalCilinicIdAndSpecIdAndGenderAndFullNameAndUserRoles(hosId, specId,gender,keyword,3L));
    /*    Set<UserEntity> users = new HashSet<>();
        if (hosId == null || hosId == 0){
            users = new HashSet<>(userRepository.findUserByKeywordAndRole_Id(keyword,3L));
        }
        else
            users = userRepository.findDoctorByHospitalCilinicId(hosId);
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
        return users;*/
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

    public Page<UserEntity> findDoctorByHospital(String keyword, Long hosId, Pageable pageable) {
        if (Objects.equals(keyword, "")) {
            return userRepository.findByHospitalCilinicDoctor_Id(hosId, pageable);

        } else
            return userRepository.findByFullNameContainingIgnoreCaseOrSpecialist_NameContainingIgnoreCaseAndHospitalCilinicDoctor_Id(keyword, keyword, hosId, pageable);
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
        List<UserEntity> userEntities;
        if (roleId == 0 && Objects.equals(keyword, "")) {
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
        }*/ else if (roleId != 0 && !Objects.equals(keyword, "")) {
            userEntities = userRepository.findUserByKeywordAndRole_Id(keyword, roleId);
        } else if (roleId != 0 && Objects.equals(keyword, "")) {
            userEntities = userRepository.findByRole_Id(roleId);
        } else {
            userEntities = userRepository.findUserByKeywordOrRole_Id(keyword, roleId);
        }
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), userEntities.size());
        return new PageImpl<>(userEntities.subList(start, end), pageable, userEntities.size());
    }

    public void changeStatus(Long userId, UserStatus status) {
        UserEntity user = userRepository.findById(userId).get();
        user.setStatus(status);
        userRepository.save(user);
    }

    public EditDoctor getDoctorInfo(Long id) {

        UserEntity userEntity = findById(id);
        EditDoctor editDoctor = new EditDoctor();
        BeanUtils.copyProperties(userEntity, editDoctor);
        editDoctor.setExperience(Long.valueOf(userEntity.getExperience().split(" ")[0]));
        editDoctor.setSpecialist(String.valueOf(userEntity.getSpecialist().getId()));
        return editDoctor;
    }
    public void updateDoctorInfo(EditDoctor editDoctor){
        UserEntity userEntity = findById(editDoctor.getId());
        userEntity.setDegree(editDoctor.getDegree());
        userEntity.setSpecialist(specialistService.findById(Long.valueOf(editDoctor.getSpecialist().trim())).get());
        userEntity.setExperience(editDoctor.getExperience().toString());
        userRepository.save(userEntity);
    }

    public void saveDoctor(AddDoctor addDoctor, String managerUsername) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(addDoctor, userEntity, "specialist", "gender", "birthday", "experience", "password", "degree");
        userEntity.setPassword(SecurityUtils.encrytePassword(addDoctor.getPassword()));
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setBirthday(LocalDate.parse(addDoctor.getBirthday()));
        userEntity.setGender(Gender.valueOf(addDoctor.getGender()));
        userEntity.setUserRoles(new HashSet<UserRoleEntity>() {
            {
                add(userRoleRepository.findByRole(Role.ROLE_DOCTOR).get());
            }
        });
        userEntity.setCreateDate(LocalDate.now());
        userEntity.setHospitalCilinicDoctor(hospitalClinicService.findByManagerUsername(managerUsername));


        userEntity.setDegree(addDoctor.getDegree());
        userEntity.setSpecialist(specialistService.findById(Long.valueOf(addDoctor.getSpecialist().trim())).get());
        userEntity.setExperience(addDoctor.getExperience().concat(" years"));
        userRepository.save(userEntity);
    }
}
