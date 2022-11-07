package com.example.doctorcare.api.domain.dto.request;

import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTimeDoctor {
    private Long id;
    private String timeStart;
    private String timeEnd;
    private String createDate;

    @Override
    public String toString() {
        return "AddTimeDoctor{" +
                "id=" + id +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
