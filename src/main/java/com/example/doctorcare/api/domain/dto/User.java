package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String email;

    private String address;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String degree;

    private String nationality;

    private String experience;

    private List<UserRole> userRoles;

    private Specialist specialist;

    private HospitalCilinic hospitalCilinicDoctor;

}
