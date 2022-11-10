package com.example.doctorcare.api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHospital {

    private Long id;

    private String name;

    private String username;

    private String address;

    private String phone;

}
