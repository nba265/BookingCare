package com.example.doctorcare.api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTimeDoctor {

    private Long id;

    private String timeStart;

    private String timeEnd;

    private String createDate;

    private String endDate;

    private String[] dateOfWeek;

    @Override
    public String toString() {
        return "AddTimeDoctor{" +
                "id=" + id +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", createDate='" + createDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
