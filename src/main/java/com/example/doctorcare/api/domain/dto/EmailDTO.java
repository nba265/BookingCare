package com.example.doctorcare.api.domain.dto;

import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EmailDTO {
    private List<String> recipients;
    private String subject;
    private AppointmentInfoForUser appointmentInfoForUser;
}
