package com.example.doctorcare.api.domain.entity;

import com.example.doctorcare.api.enums.TimeDoctorStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    @OneToOne(mappedBy = "timeDoctors",cascade = CascadeType.ALL)
    private AppointmentsEntity appointments;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private UserEntity doctor;

    @Enumerated(EnumType.STRING)
    private TimeDoctorStatus timeDoctorStatus;

    public TimeDoctorsEntity() {
    }

    public TimeDoctorsEntity(Long id) {
        this.id = id;
    }

    public TimeDoctorsEntity(LocalTime timeStart, LocalTime timeEnd, LocalDate date, UserEntity doctor, TimeDoctorStatus timeDoctorStatus) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.date = date;
        this.doctor = doctor;
        this.timeDoctorStatus = timeDoctorStatus;
    }

    public Long getId() {
        return id;
    }

    public TimeDoctorStatus getTimeDoctorStatus() {
        return timeDoctorStatus;
    }

    public void setTimeDoctorStatus(TimeDoctorStatus timeDoctorStatus) {
        this.timeDoctorStatus = timeDoctorStatus;
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

    @Override
    public String toString() {
        return "TimeDoctorsEntity{" +
                "id=" + id +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", date=" + date +
                ", appointments=" + appointments +
                ", doctor=" + doctor +
                ", timeDoctorStatus=" + timeDoctorStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDoctorsEntity that = (TimeDoctorsEntity) o;
        return Objects.equals(timeStart, that.timeStart) && Objects.equals(timeEnd, that.timeEnd) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStart, timeEnd, date);
    }
}
