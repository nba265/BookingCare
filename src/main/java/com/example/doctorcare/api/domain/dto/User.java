package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String degree;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String nationality;

    private String experience;

    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();

    private Specialist specialist;

    private HospitalClinic hospitalClinicDoctor;

    private HospitalClinic hospitalClinicMangager;

    @JsonIgnore
    private List<TimeDoctors> timeDoctors = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @JsonIgnore
    private List<Appointment> appointments  = new ArrayList<>();

}
