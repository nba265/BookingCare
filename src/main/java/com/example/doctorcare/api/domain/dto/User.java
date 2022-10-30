package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import com.example.doctorcare.api.domain.entity.SpecialistEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String email;

    private String username;

    private String address;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String degree;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String nationality;

    private String experience;

    private List<UserRole> userRoles = new ArrayList<>();

    private Specialist specialist;

    private HospitalCilinic hospitalCilinicDoctor;

    private HospitalCilinic hospitalCilinicMangager;

    private List<TimeDoctors> timeDoctors = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    private List<Appointment> appointments  = new ArrayList<>();

}
