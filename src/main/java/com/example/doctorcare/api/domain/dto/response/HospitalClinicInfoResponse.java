package com.example.doctorcare.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalClinicInfoResponse {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String username;

    private String districtCode;
}
