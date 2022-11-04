package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.enums.TimeDoctorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeDoctor {

    private Long id;
    @DateTimeFormat(pattern = "HH:mm")
    private String timeStart;

    @DateTimeFormat(pattern = "HH:mm")
    private String timeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    private String timeDoctorStatus;
}
