package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserMapper extends BaseMapper<UserEntity, User> {

    private UserRoleMapper userRoleMapper;

    @PostConstruct
    public void init(){
        this.userRoleMapper = new UserRoleMapper();
    }
    @Override
    public UserEntity convertToEntity(User dto, Object... args) {
        UserEntity userEntity = new UserEntity();
        if (dto != null){
            BeanUtils.copyProperties(dto,userEntity,"timeDoctors","userRoles");
            if (dto.getUserRoles() != null && !dto.getUserRoles().isEmpty()) {
                userEntity.setUserRoles(userRoleMapper.convertToEntitySet(dto.getUserRoles()));
            }
        }
        return userEntity;
    }

    @Override
    public User convertToDto(UserEntity entity, Object... args) {
        User user = new User();
        if (entity != null){
            BeanUtils.copyProperties(entity,user,"timeDoctors","userRoles");
            user.setUserRoles(userRoleMapper.convertToDtoList(entity.getUserRoles()));
        }
        return user;
    }
}
