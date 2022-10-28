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

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "services_id")
    private ServicesEntity services;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "customers_id")
    @JsonIgnore
    private CustomersEntity customers;

    @OneToOne(mappedBy = "appointments",fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private TimeDoctorsEntity timeDoctors;

    public AppointmentsEntity() {
    }

    public AppointmentsEntity(Long id, String comment, String description, AppointmentStatus status, LocalDateTime createDate) {
        this.id = id;
        this.comment = comment;
        this.description = description;
        this.status = status;
        this.createDate = createDate;
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
}
