package com.example.doctorcare.api.domain.dto.request;

import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddService {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Long HospitalId;
}
