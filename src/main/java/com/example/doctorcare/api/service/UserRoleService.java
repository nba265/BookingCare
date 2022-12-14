package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    public Optional<UserRoleEntity> findById(Long id){
        return userRoleRepository.findById(id);
    }
}
