package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.domain.dto.request.AddHospital;
import com.example.doctorcare.api.domain.dto.request.ChangeStatus;
import com.example.doctorcare.api.domain.dto.response.MessageResponse;
import com.example.doctorcare.api.domain.dto.response.UserInformationForAdmin;
import com.example.doctorcare.api.domain.entity.HospitalClinicEntity;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.enums.UserStatus;
import com.example.doctorcare.api.service.HospitalClinicService;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.PaginationAndSortUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private HospitalClinicService hospitalClinicService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    PaginationAndSortUtil paginationAndSortUtil = new PaginationAndSortUtil();

    @GetMapping("/listHospital")
    public ResponseEntity<?> getAllHospital() {
        try {
            return new ResponseEntity<>(hospitalClinicService.hospitalCilinicList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkUser")
    public ResponseEntity<?> checkUserCreate(@RequestParam(value = "username") String username) {
        try {
                return new ResponseEntity<>(userDetailsService.checkUser(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    @GetMapping("/checkUserUpdate")
    public ResponseEntity<?> checkUserUpdate(@RequestParam("username") String username) {
        try {
            return new ResponseEntity<>(userDetailsService.checkUser(username), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "role", required = false, defaultValue = "0") String roleId,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size) {
        try {
            List<UserInformationForAdmin> userInformationForAdmins = new ArrayList<>();
            List<UserEntity> userEntities;
            Pageable pagingSort = paginationAndSortUtil.paginate(page, size, null);
            Page<UserEntity> pageTuts = userDetailsService.findUser(keyword, Long.parseLong(roleId), pagingSort);
            userEntities = pageTuts.getContent();
            if (userEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userEntities.forEach(user -> {
                UserInformationForAdmin userInformation = new UserInformationForAdmin();
                BeanUtils.copyProperties(user, userInformation, "createDate", "birthday");
                if (user.getSpecialist() != null) {
                    userInformation.setSpecialist(user.getSpecialist().getName());
                }
                userInformation.setGender(user.getGender().toString());
                userInformation.setStatus(user.getStatus().toString());
                userInformation.setCreateDate(user.getCreateDate().toString());
                if (user.getHospitalCilinicDoctor() != null) {
                    userInformation.setHospitalName(user.getHospitalCilinicDoctor().getName());
                }
                if (user.getHospitalCilinicMangager() != null) {
                    userInformation.setHospitalName(user.getHospitalCilinicMangager().getName());
                }
                userInformation.setRole(user.getUserRoles().stream().findFirst().get().getRole().toString());
                userInformation.setBirthday(user.getBirthday().toString());
                userInformationForAdmins.add(userInformation);
            });
            Map<String, Object> response = new HashMap<>();
            response.put("userInformationForAdmins", userInformationForAdmins);
            response.put("currentPage", pageTuts.getNumber() + 1);
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addHospital")
    public ResponseEntity<?> addHospital(@RequestBody AddHospital hospitalCilinic) {
        try {
            UserEntity user = userDetailsService.findByUsername(hospitalCilinic.getUsername()).get();
            HospitalClinicEntity hospitalClinicEntity;
            if (hospitalCilinic.getId() == null) {
                hospitalClinicEntity = new HospitalClinicEntity();
            } else {
                hospitalClinicEntity = hospitalClinicService.findById(hospitalCilinic.getId());
            }
            hospitalClinicEntity.setAddress(hospitalCilinic.getAddress());
            hospitalClinicEntity.setPhone(hospitalCilinic.getPhone());
            hospitalClinicEntity.setManager(user);
            hospitalClinicEntity.setName(hospitalCilinic.getName());
            hospitalClinicEntity.setDistrictCode(hospitalCilinic.getDistrictCode());
            hospitalClinicService.save(hospitalClinicEntity);
            user.setHospitalCilinicDoctor(hospitalClinicEntity);
            return new ResponseEntity<>(new MessageResponse("success"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/changeUserStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatus changeStatus) {
        try {
            userDetailsService.changeStatus(Long.valueOf(changeStatus.getId()), UserStatus.valueOf(changeStatus.getStatus()));
            return new ResponseEntity<>("Success!!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
