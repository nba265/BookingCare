package com.example.doctorcare.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Specialist {

    private Long id;

    private String name;

    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @JsonIgnore
    private HospitalClinic hospitalClinic;
}
