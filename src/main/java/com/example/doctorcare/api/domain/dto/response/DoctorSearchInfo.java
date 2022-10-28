package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.dto.TimeDoctors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSearchInfo {

    private String hospName;

    private String specialist;

    private String doctorName;

    private List<Services> servicesList = new ArrayList<>();

    private List<ListTimeDoctor> timeDoctors = new ArrayList<>();

}
