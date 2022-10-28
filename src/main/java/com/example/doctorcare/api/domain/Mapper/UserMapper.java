package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserMapper extends BaseMapper<UserEntity, User> {

    UserRoleMapper userRoleMapper;

    TimeDoctorsMapper timeDoctorsMapper;


    @PostConstruct
    public void init() {
        this.userRoleMapper = new UserRoleMapper();
        this.timeDoctorsMapper = new TimeDoctorsMapper();
    }

    @Override
    public UserEntity convertToEntity(User dto, Object... args) {
        UserEntity userEntity = new UserEntity();
        userRoleMapper = new UserRoleMapper();
        timeDoctorsMapper = new TimeDoctorsMapper();
        if (dto != null) {
            BeanUtils.copyProperties(dto, userEntity, "timeDoctors", "userRoles");
            if (dto.getUserRoles() != null && !dto.getUserRoles().isEmpty()) {
                userEntity.setUserRoles(userRoleMapper.convertToEntitySet(dto.getUserRoles()));
            }
            if (dto.getTimeDoctors() != null && !dto.getTimeDoctors().isEmpty()) {
                userEntity.setTimeDoctors(timeDoctorsMapper.convertToEntitySet(dto.getTimeDoctors()));
            }
        }
        return userEntity;
    }

    @Override
    public User convertToDto(UserEntity entity, Object... args) {
        User user = new User();
        userRoleMapper = new UserRoleMapper();
        timeDoctorsMapper = new TimeDoctorsMapper();
        if (entity != null) {
            BeanUtils.copyProperties(entity, user, "timeDoctors", "userRoles");
            if (entity.getUserRoles() != null && !entity.getUserRoles().isEmpty()) {
                user.setUserRoles(userRoleMapper.convertToDtoList(entity.getUserRoles()));
            }
            if (entity.getTimeDoctors() != null && !entity.getTimeDoctors().isEmpty()) {
                user.setTimeDoctors(timeDoctorsMapper.convertToDtoList(entity.getTimeDoctors()));
            }
        }
        return user;
    }
}
