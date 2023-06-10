package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createUser = userService.createUser(userDto);

        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);

    }

    //update

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

        UserDto userDto1 = userService.updateUser(userDto, userId);

        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);
    }

    //delete

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {

        userService.deleteUser(userId);

        return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
    }

    //getAllUser

    @GetMapping("/AllUser")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> allUser = userService.getAllUser();

        return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.OK);

    }

    //user by id
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {

        UserDto userById = userService.getUserById(userId);

        return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
    }

    //user by email

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        UserDto byEmail = userService.getUserByEmail(email);

        return new ResponseEntity<UserDto>(byEmail, HttpStatus.OK);
    }

    //search keyword

    @GetMapping("/searchKeyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        List<UserDto> user = userService.searchUser(keyword);

        return new ResponseEntity<List<UserDto>>(user, HttpStatus.OK);
    }


}