package com.example.doctorcare.api.domain.dto.request;

import com.example.doctorcare.api.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeAppointment {

    private Long doctorId;

    private Long servId;

    private Long timeDoctorId;

    private String fullName;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Max(200)
    private String description;

    @Length(max = 12,min = 12)
    private String identityCard;
}
