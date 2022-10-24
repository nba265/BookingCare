/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.domain.entity;


import com.example.doctorcare.api.enums.Gender;
import com.example.doctorcare.api.enums.UserStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( length = 100)
    private String email;

    private String password;

    private String address;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String degree;

    private String nationality;

    private String experience;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_relationship",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"))
    private Set<UserRoleEntity> userRoles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "specialist_id")
    private SpecialistEntity specialist;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    private HospitalCilinicEntity hospitalCilinicMangager;

    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "hospital_cilinic_id")
    private HospitalCilinicEntity hospitalCilinicDoctor;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "doctor")
    private Set<TimeDoctorsEntity> timeDoctors;

    public UserEntity() {
    }

    public UserEntity(String email, String password, String address, String fullName, Gender gender, String phone, String degree, String nationality, String experience, UserStatus status, LocalDate createDate, Set<UserRoleEntity> userRoles) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.degree = degree;
        this.nationality = nationality;
        this.experience = experience;
        this.status = status;
        this.createDate = createDate;
        this.userRoles = userRoles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    public HospitalCilinicEntity getHospitalCilinicMangager() {
        return hospitalCilinicMangager;
    }

    public void setHospitalCilinicMangager(HospitalCilinicEntity hospitalCilinicMangager) {
        this.hospitalCilinicMangager = hospitalCilinicMangager;
    }

    public HospitalCilinicEntity getHospitalCilinicDoctor() {
        return hospitalCilinicDoctor;
    }

    public void setHospitalCilinicDoctor(HospitalCilinicEntity hospitalCilinicDoctor) {
        this.hospitalCilinicDoctor = hospitalCilinicDoctor;
    }

    public Set<TimeDoctorsEntity> getTimeDoctors() {
        return timeDoctors;
    }

    public void setTimeDoctors(Set<TimeDoctorsEntity> timeDoctors) {
        this.timeDoctors = timeDoctors;
    }
}
