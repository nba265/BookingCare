package com.example.doctorcare.api.domain.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddDoctor {

    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    private String gender;

    private String nationality;

    private String phone;

    private String experience;

    private String specialist;

    private String degree;
}
