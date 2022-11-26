package com.example.doctorcare.api.domain.dto.request;

import lombok.Data;

@Data
public class EditDoctor {
    private Long id;
    private String degree;
    private Long experience;
    private String specialist;
}
