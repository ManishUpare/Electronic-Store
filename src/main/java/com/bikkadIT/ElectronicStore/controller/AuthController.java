package com.bikkadIT.ElectronicStore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/currentName")
    public ResponseEntity<String> getCurrentUser(Principal principal) {
        String name = principal.getName();
        return new ResponseEntity<>(name, HttpStatus.OK);

    }

}
