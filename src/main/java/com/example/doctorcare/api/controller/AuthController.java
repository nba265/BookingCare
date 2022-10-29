package com.example.doctorcare.api.controller;

import com.example.doctorcare.api.common.UserRole;
import com.example.doctorcare.api.config.security.JWT.JwtUtils;
import com.example.doctorcare.api.config.security.Services.UserDetailsImpl;
import com.example.doctorcare.api.domain.dto.User;
import com.example.doctorcare.api.domain.dto.request.LoginRequest;
import com.example.doctorcare.api.domain.dto.request.SignupRequest;
import com.example.doctorcare.api.domain.dto.response.JwtResponse;
import com.example.doctorcare.api.domain.dto.response.MessageResponse;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.domain.entity.UserRoleEntity;
import com.example.doctorcare.api.enums.Role;
import com.example.doctorcare.api.enums.UserStatus;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.repository.UserRoleRepository;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import com.example.doctorcare.api.utilis.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign_in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if (SecurityUtils.checkInfoLogin(loginRequest,userDetailsService,encoder)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("Error: Wrong Username or Password"));
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        if(!signUpRequest.getRePassword().equals(signUpRequest.getPassword())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password is not the same"));
        }

        // Create new user's account
        UserEntity user = new UserEntity(signUpRequest.getEmail(),signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        user.setFullName(signUpRequest.getFullName());

        user.getUserRoles().add(roleRepository.findByRole(Role.ROLE_USER).get());

        user.setStatus(UserStatus.ACTIVE);

        user.setCreateDate(LocalDate.now());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
