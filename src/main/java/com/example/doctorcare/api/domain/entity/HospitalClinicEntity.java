package com.example.doctorcare.api.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "hospital_cilinic")
public class HospitalClinicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Long id;

    private String name;

    private String address;

    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private UserEntity manager;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "hospitalCilinicDoctor")
    private Set<UserEntity> doctor;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospitalCilinic")
    private Set<ServicesEntity> services;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospitalCilinic")
    private Set<SpecialistEntity> specialists;

    public HospitalClinicEntity() {
    }

    public HospitalClinicEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public UserEntity getManager() {
        return manager;
    }

    public void setManager(UserEntity manager) {
        this.manager = manager;
    }

    public Set<UserEntity> getDoctor() {
        return doctor;
    }

    public void setDoctor(Set<UserEntity> doctor) {
        this.doctor = doctor;
    }

    public Set<ServicesEntity> getServices() {
        return services;
    }

    public void setServices(Set<ServicesEntity> services) {
        this.services = services;
    }

    public Set<SpecialistEntity> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(Set<SpecialistEntity> specialists) {
        this.specialists = specialists;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
