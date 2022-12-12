package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.CustomersEntity;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;

    private String comment;

    private String description;

    private AppointmentStatus status;

    private LocalDateTime createDate;

    private Services services;

    @JsonIgnore
    private Customer customer;

    @JsonIgnore
    private TimeDoctors timeDoctors;

    @JsonIgnore
    private User user;

    private String appointmentCode;

    private String cancelReason;
}
