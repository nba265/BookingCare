package com.example.doctorcare.api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class AddService {
    private Long id;

    private String name;

    private String description;

    private String price;

    private String serviceEnum;


}
