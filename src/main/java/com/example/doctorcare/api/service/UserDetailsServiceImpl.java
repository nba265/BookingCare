/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.service;


import com.example.doctorcare.api.domain.Mapper.UserMapper;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmailLikeAndStatusLike(email, UserStatus.ACTIVE);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        Set<UserRoleEntity> roleNames = userRoleRepository.findByUsers_Email(email);
        Set<GrantedAuthority> grantList = new HashSet<>();
        if (!CollectionUtils.isEmpty(roleNames)) {
            for (UserRoleEntity role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole().toString());
                grantList.add(authority);
            }
        }

        return (UserDetails) new User(user.getEmail(), user.getPassword(), grantList);
    }

    public Iterable<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public void save(UserEntity userEntity){

            userRepository.save(userEntity);


    }

    public UserEntity findByEmail(String email){
        return userRepository.findByEmailLikeAndStatusLike(email,UserStatus.ACTIVE);
    }

    public void save(com.example.doctorcare.api.domain.dto.User user){
        user.setCreateDate(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(userMapper.convertToEntity(user));
    }

    public List<com.example.doctorcare.api.domain.dto.User> findDoctorByHospitalCilinic(Long hosId){
        List<com.example.doctorcare.api.domain.dto.User> users = new ArrayList<>();
        userRepository.findDoctorByHospitalCilinicId(hosId).forEach(userEntity -> users.add(userMapper.convertToDto(userEntity)));
        return users;
    }

    public List<com.example.doctorcare.api.domain.dto.User> findDoctor(Long hosId, Long specId, String gender, String keyword){
        Set<UserEntity> users =  userRepository.findDoctorByHospitalCilinicId(hosId);
        Set<UserEntity> toRemove = new HashSet<>();
        if(specId != null && specId != 0){
            users.forEach(user -> {
                if (!Objects.equals(user.getSpecialist().getId(), specId)){
                    toRemove.add(user);
                }
            });
        }
        if (gender != null && !gender.equals("")){
            users.forEach(user -> {
                if (!user.getGender().toString().equalsIgnoreCase(gender)){
                    toRemove.add(user);
                }
            });
        }
        if (keyword != null && !keyword.equals("")){
            users.forEach(user -> {
                if (!user.getFullName().contains(keyword)){
                    toRemove.add(user);
                }
            });
        }
        users.removeAll(toRemove);
        return userMapper.convertToDtoList(users);
    }
}
