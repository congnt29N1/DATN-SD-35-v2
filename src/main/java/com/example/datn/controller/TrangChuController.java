package com.example.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController {
    @GetMapping("/index")
    public String home(){
        return "userIndex";
    }
}
