package com.example.doctorcare.api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddSpecialist {

    private Long id;

    private String name;
}
