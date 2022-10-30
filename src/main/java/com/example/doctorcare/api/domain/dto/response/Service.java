
package com.example.doctorcare.api.domain.dto.response;

import com.example.doctorcare.api.enums.ServiceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private String serviceEnum;
}
