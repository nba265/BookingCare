package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.entity.ServicesEntity;
import com.example.doctorcare.api.enums.ServiceEnum;
import com.example.doctorcare.api.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    public List<Services> findAllByHospitalCilinic_Id(Long id){
        List<Services> services = serviceMapper.convertToDtoList(servicesRepository.findAllByHospitalCilinic_Id(id));
        services.removeIf(services1 -> (
             services1.getServiceEnum().equals(ServiceEnum.UNAVAILABLE)));
        return services;
    }
    public List<Services> findAllByHospitalCilinic_IdManager(Long id){

        return serviceMapper.convertToDtoList(servicesRepository.findAllByHospitalCilinic_Id(id));
    }

    public Optional<ServicesEntity> findById(Long id){
        return servicesRepository.findById(id);
    }


    public void toggleStatus(String name,Long id) {
        ServicesEntity services = servicesRepository.findById(id).get();
        if (!services.getServiceEnum().equals(ServiceEnum.valueOf(name))) {
            services.setServiceEnum(ServiceEnum.valueOf(name));
            servicesRepository.save(services);
        }
    }

    public void save(Services services) {
        servicesRepository.save(serviceMapper.convertToEntity(services));
    }


    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void saveEntity(ServicesEntity servicesEntity) throws Exception {
        try {
            servicesRepository.save(servicesEntity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
