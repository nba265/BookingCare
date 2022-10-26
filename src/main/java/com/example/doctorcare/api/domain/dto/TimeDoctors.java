package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.entity.AppointmentsEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeDoctors {

    private Long id;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeStart;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private AppointmentsEntity appointments;

    private UserEntity doctor;

    @Override
    public String toString() {
        return "TimeDoctors{" +
                "id=" + id +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", date=" + date +
                '}';
    }
}
