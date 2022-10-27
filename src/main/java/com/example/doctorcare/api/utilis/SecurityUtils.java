/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.utilis;

import com.example.doctorcare.api.domain.dto.request.LoginRequest;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.repository.UserRepository;
import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 *
 * @author ldanh
 */
public class SecurityUtils {

    public static List<String> getRolesOfUser() {
        List<String> roles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                Collection<GrantedAuthority> authoritys = (Collection<GrantedAuthority>) userDetails.getAuthorities();
                if (!CollectionUtils.isEmpty(authoritys)) {
                    for (GrantedAuthority authority : authoritys) {
                        roles.add(authority.getAuthority());
                    }
                }
            }
        }
        return roles;
    }

    public static String getUsername() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            /*            username = principal.toString();*/
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    public static boolean checkIfLogin(){
        return (SecurityUtils.getUsername() != null && !Objects.equals(SecurityUtils.getUsername(), ""));
    }


    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean checkInfoLogin(LoginRequest loginRequest, UserDetailsServiceImpl userDetailsService){
        Optional<UserEntity> user = userDetailsService.findByUsername(loginRequest.getUsername());
        if(user.isPresent()){
            if(user.get().getPassword().equals(encrytePassword(loginRequest.getPassword())));
            return true;
        }
        else return false;
    }
}
