package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.HospitalCilinicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Services {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private HospitalCilinic hospitalCilinic;
}