package com.example.doctorcare.api.domain.entity;

import com.example.doctorcare.api.enums.ServiceEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "services")
public class ServicesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;
    @Enumerated(EnumType.STRING)
    private ServiceEnum serviceEnum;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_cilinic_id")
    private HospitalCilinicEntity hospitalCilinic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "services",fetch = FetchType.LAZY)
    private Set<AppointmentsEntity> appointmentsSet;

    public ServicesEntity() {
    }

    public ServicesEntity(Long id, String name, String description, Double price, ServiceEnum serviceEnum, HospitalCilinicEntity hospitalCilinic, Set<AppointmentsEntity> appointmentsSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.serviceEnum = serviceEnum;
        this.hospitalCilinic = hospitalCilinic;
        this.appointmentsSet = appointmentsSet;
    }

    public ServiceEnum getServiceEnum() {
        return serviceEnum;
    }

    public void setServiceEnum(ServiceEnum serviceEnum) {
        this.serviceEnum = serviceEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public HospitalCilinicEntity getHospitalCilinic() {
        return hospitalCilinic;
    }

    public void setHospitalCilinic(HospitalCilinicEntity hospitalCilinic) {
        this.hospitalCilinic = hospitalCilinic;
    }

    public Set<AppointmentsEntity> getAppointmentsSet() {
        return appointmentsSet;
    }

    public void setAppointmentsSet(Set<AppointmentsEntity> appointmentsSet) {
        this.appointmentsSet = appointmentsSet;
    }
}
