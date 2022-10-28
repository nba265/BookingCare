package com.example.doctorcare.api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeAppointment {

    private Long hosId;

    private Long specId;

    private Long doctorId;

    private Long servId;
}