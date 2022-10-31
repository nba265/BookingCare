package com.example.doctorcare.api.utilis;

import com.example.doctorcare.api.enums.Role;

public class RoleConst {

    public static String getRoleConst(String role) {
        String returnRole = "";
        switch (role) {
            case "ROLE_ADMIN":
                returnRole = "admin";
                break;
            case "ROLE_MANAGER":
                returnRole = "manager";
                break;
            case "ROLE_DOCTOR":
                returnRole = "doctor";
                break;
            case "ROLE_USER":
                returnRole = "user";
                break;
        }
        return returnRole;
    }
}
