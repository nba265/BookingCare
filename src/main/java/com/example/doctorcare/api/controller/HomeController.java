package com.mycompany.spring_mvc_project_final.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/index","/"})
    public String home(){
        System.out.println("asdds");
        return "index";
    }
}
