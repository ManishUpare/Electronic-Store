package com.bikkadIT.ElectronicStore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Homecontroller {

    @GetMapping
    public String testing(){

        return "Welcome to bikkad IT";
    }
}