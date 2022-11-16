package com.example.doctorcare.api.utilis;

public class RoleConst {

    public static String getRoleConst(String role) {
        return switch (role) {
            case "ROLE_ADMIN" -> "admin";
            case "ROLE_MANAGER" -> "manager";
            case "ROLE_DOCTOR" -> "doctor";
            case "ROLE_USER" -> "user";
            default -> "";
        };
    }
}
