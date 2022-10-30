package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.Mapper.HospitalCilinicMapper;
import com.example.doctorcare.api.domain.Mapper.ServiceMapper;
import com.example.doctorcare.api.domain.dto.HospitalCilinic;
import com.example.doctorcare.api.domain.dto.Services;
import com.example.doctorcare.api.domain.dto.request.AddService;
import com.example.doctorcare.api.service.HospitalCilinicService;
import com.example.doctorcare.api.service.ServicesService;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/manager")
public class ManagerController {
    @Autowired
    HospitalCilinicMapper hospitalCilinicMapper;
    @Autowired
    ServicesService servicesService;
    @Autowired
    HospitalCilinicService hospitalCilinicService;
    @Autowired
    ServiceMapper serviceMapper;

    @PostMapping("/create_edit_service")
    public ResponseEntity<?> createTimeService(@RequestBody AddService service) {
        try {
            HospitalCilinic hospitalCilinic = hospitalCilinicService.findByManagerUsername(SecurityUtils.getUsername());
            Services services = new Services();
            if (service.getId() == null) {
                services.setId(0L);
            } else services.setId(service.getId());
            services.setName(service.getName());
            services.setPrice(service.getPrice());
            services.setDescription(service.getDescription());
            services.setHospitalCilinic(hospitalCilinic);
            servicesService.save(services);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("get_all_service")
    public ResponseEntity<?> getAllService() {
        try {
            return new ResponseEntity<>(servicesService.findAllByHospitalCilinic_Id(hospitalCilinicService.findByManagerUsername(SecurityUtils.getUsername()).getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get_service_by_id")
    public ResponseEntity<?> displayEditSerivice(Long id) {
        try {
            return new ResponseEntity<>(serviceMapper.convertToDto(servicesService.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("delete_service_by_id")
    public ResponseEntity<?> deleteService(Long id) {
        try {
            return new ResponseEntity<>(serviceMapper.convertToDto(servicesService.findById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("edit_status")
    public ResponseEntity<?> changeStatus(@RequestParam("status") String status,@RequestParam("id") Long id){
        try {
            servicesService.toggleStatus(status,id);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
