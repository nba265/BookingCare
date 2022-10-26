package com.example.doctorcare.api.exception;

import com.example.doctorcare.api.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiExceptionResponse {
    private ErrorCode code;
    private String message;
    private int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private List<ApiError> errors;

    public void addValidationError(ApiError build) {
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(build);
    }
}
