package com.example.doctorcare.api.domain.Mapper;


import com.example.doctorcare.api.domain.dto.UserRole;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserRoleMapper extends BaseMapper<UserRoleEntity, UserRole> {

    private UserMapper userMapper;

    @PostConstruct
    public void init(){
        this.userMapper = new UserMapper();
    }
    @Override
    public UserRoleEntity convertToEntity(UserRole dto, Object... args) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userMapper = new UserMapper();
        if (dto != null) {
            BeanUtils.copyProperties(dto, userRoleEntity, "users");
/*            if (dto.getUsers() != null && !dto.getUsers().isEmpty()){
                userRoleEntity.setUsers(userMapper.convertToEntitySet(dto.getUsers()));
            }*/
        }
        return userRoleEntity;
    }

    @Override
    public UserRole convertToDto(UserRoleEntity entity, Object... args) {
        UserRole userRole = new UserRole();
        userMapper = new UserMapper();
        if (entity != null){
            BeanUtils.copyProperties(entity,userRole,"users");
/*            if (entity.getUsers() != null && !entity.getUsers().isEmpty()){
                userRole.setUsers(userMapper.convertToDtoList(entity.getUsers()));
            }*/
        }
        return userRole;
    }
}
