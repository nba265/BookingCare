package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.CustomersEntity;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;
import com.example.doctorcare.api.enums.AppointmentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

public class Appoinment {
    private Long id;

    private String comment;

    private String description;


    private AppointmentStatus status;

    private LocalDateTime createDate;

    private Services services;

    private Customer customer;

    private TimeDoctors timeDoctors;
}
