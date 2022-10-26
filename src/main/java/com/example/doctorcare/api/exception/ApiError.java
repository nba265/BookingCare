package com.example.doctorcare.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@JsonIgnoreProperties
public class ApiError {
    private String field;
    private String msg;
}
