package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.domain.dto.UserRole;
import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfoResponse {

    private Long id;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String degree;

    private String nationality;

    private String experience;

    private Specialist specialist;
}
