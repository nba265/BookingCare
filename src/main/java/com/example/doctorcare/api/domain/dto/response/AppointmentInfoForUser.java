package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentInfoForUser {

    private String namePatient;

    private String phonePatient;

    private String birthday;

    private String genderCustomer;

    private String doctorName;

    private String genderDoctor;

    private String phoneDoctor;

    private String specialistDoctor;

    private AppointmentStatus status;

    private String description;

    private String service;

    private String price;

    private AppointmentHistory appointmentHistory;

    @Override
    public String toString() {
        return "More Information{" +
                "\nnamePatient='" + namePatient + '\'' +
                ", \nphonePatient='" + phonePatient + '\'' +
                ", \nbirthday='" + birthday + '\'' +
                ", \ngenderCustomer='" + genderCustomer + '\'' +
                ", \ndoctorName='" + doctorName + '\'' +
                ", \ngenderDoctor='" + genderDoctor + '\'' +
                ", \nphoneDoctor='" + phoneDoctor + '\'' +
                ", \nspecialistDoctor='" + specialistDoctor + '\'' +
                ", \nstatus=" + status +
                ", \ndescription='" + description + '\'' +
                ", \nservice='" + service + '\'' +
                ", \nprice='" + price + '\'' +
                '}';
    }
}
