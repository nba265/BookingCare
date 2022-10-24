package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    private Integer id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private List<User> users;
}
