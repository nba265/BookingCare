package com.example.doctorcare.api.domain.Mapper;

import com.example.doctorcare.api.domain.dto.Customer;
import com.example.doctorcare.api.domain.entity.CustomersEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper extends BaseMapper<CustomersEntity, Customer> {


    @Override
    public CustomersEntity convertToEntity(Customer dto, Object... args) {
        CustomersEntity customers = new CustomersEntity();
        if (dto != null)
            BeanUtils.copyProperties(dto,customers);
        return customers;
    }

    @Override
    public Customer convertToDto(CustomersEntity entity, Object... args) {
        Customer customer = new Customer();
        if (entity != null)
            BeanUtils.copyProperties(entity,customer);
        return customer;
    }
}
