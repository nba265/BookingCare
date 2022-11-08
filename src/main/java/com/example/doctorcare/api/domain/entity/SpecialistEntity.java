package com.example.doctorcare.api.domain.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Specialist")
public class SpecialistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "specialist")
    private Set<UserEntity> userSet;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_cilinic_id")
    private HospitalClinicEntity hospitalCilinic;

    public SpecialistEntity() {
    }

    public SpecialistEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserEntity> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<UserEntity> userSet) {
        this.userSet = userSet;
    }

    public HospitalClinicEntity getHospitalCilinic() {
        return hospitalCilinic;
    }

    public void setHospitalCilinic(HospitalClinicEntity hospitalCilinic) {
        this.hospitalCilinic = hospitalCilinic;
    }
}
