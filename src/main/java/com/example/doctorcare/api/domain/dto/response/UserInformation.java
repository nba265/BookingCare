package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.domain.dto.Specialist;
import com.example.doctorcare.api.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

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

    public String birthday;

    public String username;

    @Email
    public String email;

    private String address;

    @NotEmpty
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private String gender;

    private String phone;

    private String degree;

    private String nationality;
    private String specialist;
    private String experience;

    private String createDate;

    private String status;

}
