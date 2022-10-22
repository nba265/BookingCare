package com.example.doctorcare.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/index","/"})
    public String index(){
        System.out.println("asdds");
        return "index";
    }
    @GetMapping({"/home"})
    public String home(){
        System.out.println("kakakkaka");
        return "home";
    }
}
