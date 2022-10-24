package com.example.doctorcare.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalCilinic {

    private Long id;

    private String name;

    private List<User> doctor;

    private List<Services> services;
}
