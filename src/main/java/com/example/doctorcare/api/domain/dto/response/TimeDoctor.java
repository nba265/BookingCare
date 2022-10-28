package com.example.doctorcare.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
}
