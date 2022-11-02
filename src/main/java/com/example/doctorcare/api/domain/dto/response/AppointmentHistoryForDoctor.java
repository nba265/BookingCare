package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.enums.AppointmentStatus;
import com.example.doctorcare.api.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentHistoryForDoctor {

    private String namePatient;

    private String phonePatient;

    private String birthday;

    private Gender genderCustomer;

    private String specialist;

    private String appointmentCode;
}
