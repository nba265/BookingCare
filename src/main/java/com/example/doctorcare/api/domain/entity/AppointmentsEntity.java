package com.example.doctorcare.api.domain.entity;


import com.example.doctorcare.api.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class AppointmentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String comment;

    private String description;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalDateTime createDate;

    private String appointmentCode;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "services_id")
    private ServicesEntity services;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "customers_id")
    @JsonIgnore
    private CustomersEntity customers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_doctors_id",referencedColumnName = "id")
    private TimeDoctorsEntity timeDoctors;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cancel_time_doctors_id",referencedColumnName = "id")
    private TimeDoctorsEntity cancelTimeDoctors;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public AppointmentsEntity() {
    }

    public AppointmentsEntity(Long id, String comment, String description, AppointmentStatus status, LocalDateTime createDate) {
        this.id = id;
        this.comment = comment;
        this.description = description;
        this.status = status;
        this.createDate = createDate;
    }

    public TimeDoctorsEntity getCancelTimeDoctors() {
        return cancelTimeDoctors;
    }

    public void setCancelTimeDoctors(TimeDoctorsEntity cancelTimeDoctors) {
        this.cancelTimeDoctors = cancelTimeDoctors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public ServicesEntity getServices() {
        return services;
    }

    public void setServices(ServicesEntity services) {
        this.services = services;
    }

    public CustomersEntity getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersEntity customers) {
        this.customers = customers;
    }

    public TimeDoctorsEntity getTimeDoctors() {
        return timeDoctors;
    }

    public void setTimeDoctors(TimeDoctorsEntity timeDoctors) {
        this.timeDoctors = timeDoctors;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
