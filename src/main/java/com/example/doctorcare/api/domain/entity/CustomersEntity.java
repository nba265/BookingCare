package com.example.doctorcare.api.domain.entity;


import com.example.doctorcare.api.enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Customers")
public class CustomersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name_booking")
    private String nameBooking;

    @Column(name = "phone_booking")
    private String phoneBooking;

    @Column(name = "name_patient")
    private String namePatient;

    @Column(name = "phone_patient")
    private String phonePatient;

    @Column(name = "email")
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customers")
    private Set<AppointmentsEntity> appointmentsSet;

    public CustomersEntity() {
    }

    public CustomersEntity(Long id, String nameBooking, String phoneBooking, String namePatient, String phonePatient, String email, Gender gender, LocalDate birthday) {
        this.id = id;
        this.nameBooking = nameBooking;
        this.phoneBooking = phoneBooking;
        this.namePatient = namePatient;
        this.phonePatient = phonePatient;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBooking() {
        return nameBooking;
    }

    public void setNameBooking(String nameBooking) {
        this.nameBooking = nameBooking;
    }

    public String getPhoneBooking() {
        return phoneBooking;
    }

    public void setPhoneBooking(String phoneBooking) {
        this.phoneBooking = phoneBooking;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient = phonePatient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<AppointmentsEntity> getAppointmentsSet() {
        return appointmentsSet;
    }

    public void setAppointmentsSet(Set<AppointmentsEntity> appointmentsSet) {
        this.appointmentsSet = appointmentsSet;
    }

}
