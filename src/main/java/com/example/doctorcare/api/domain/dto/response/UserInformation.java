package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation {

    public Long id;

    @NotEmpty
    public String birthday;

    @Email
    public String email;

    private String address;

    @NotEmpty
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String phone;

    private String nationality;

}
