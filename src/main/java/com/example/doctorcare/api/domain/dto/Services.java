package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.enums.ServiceEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Services {

    private Long id;

    private String name;

    private String description;

    private Double price;

    @JsonIgnore
    private HospitalClinicEntity hospitalCilinic;

    @Enumerated(EnumType.STRING)
    private ServiceEnum serviceEnum;

    @JsonIgnore
    private List<AppointmentsEntity> appointments;



}
