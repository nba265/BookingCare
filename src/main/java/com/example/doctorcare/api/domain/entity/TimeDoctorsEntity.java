package com.example.doctorcare.api.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "time_doctors")
public class TimeDoctorsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time_start")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeStart;

    @Column(name = "time_end")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeEnd;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    private AppointmentsEntity appointments;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private UserEntity doctor;

    public TimeDoctorsEntity() {
    }

    public TimeDoctorsEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AppointmentsEntity getAppointments() {
        return appointments;
    }

    public void setAppointments(AppointmentsEntity appointments) {
        this.appointments = appointments;
    }

    public UserEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(UserEntity doctor) {
        this.doctor = doctor;
    }
}
