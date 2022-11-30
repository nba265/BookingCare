package com.example.doctorcare.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentHistory {

    private Long id;

    private String hospitalName;

    @DateTimeFormat(pattern = "HH:mm")
    private String timeStart;

    @DateTimeFormat(pattern = "HH:mm")
    private String timeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    private String appointmentCode;

    private String status;

    private String hospitalAddress;

    private String hospitalPhone;

    private String createDate;

    @Override
    public String toString() {
        return "AppointmentHistory{" +
                "\nhospitalName='" + hospitalName + '\'' +
                ", \ntimeStart='" + timeStart + '\'' +
                ", \ntimeEnd='" + timeEnd + '\'' +
                ", \ndate='" + date + '\'' +
                ", \nappointmentCode='" + appointmentCode + '\'' +
                ", \nstatus='" + status + '\'' +
                ", \nhospitalAddress='" + hospitalAddress + '\'' +
                '}';
    }
}
