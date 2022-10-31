package com.example.doctorcare.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalClinic {

    private Long id;

    private String name;

    private User manager;

    private String address;

    private String phone;

    private List<User> doctor = new ArrayList<>();

    private List<Services> services = new ArrayList<>();

    private List<Specialist> specialists = new ArrayList<>();
}
