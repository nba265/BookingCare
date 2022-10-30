package com.example.doctorcare.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentHistoryManager extends AppoinmentHistory {
    private String namePatient;

    private String phonePatient;

    private String birthday;

    private String genderCustomer;

    private String doctorName;

    private String genderDoctor;

    private String phoneDoctor;

    private String specialistDoctor;


    private String serviceName;
}
