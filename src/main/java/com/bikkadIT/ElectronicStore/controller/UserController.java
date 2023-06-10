package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * @Author Manish Upare
     * @param userDto
     * @apiNote This method is for Creating User
     * @return
     */

    Logger logger= LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        logger.info("Entering for save the User Details");
        UserDto createUser = userService.createUser(userDto);

        logger.info("Completed the request for create user Details");
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);

    }

    /**@apiNote This method is for Updating User
     * @param userDto
     * @param userId
     * @return
     */


    //update

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

        logger.info("Entering the request for update user details with userId:{}",userId);
        UserDto userDto1 = userService.updateUser(userDto, userId);

        logger.info("Completed the request for update user details with userId:{}",userId);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for Deleting User
     * @param userId
     * @return
     */

    //delete

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {

        logger.info("Entering the request for delete user details with userId:{}",userId);
        userService.deleteUser(userId);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(AppConstant.USER_DELETE);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        logger.info("Completed the request for delete details with userId:{}",userId);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

    /**
     * @apiNote ThiS method is for Getting All User
     * @return
     */

    //getAllUser

    @GetMapping("/AllUser")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        logger.info("Entering the request for get all user data");
        List<UserDto> allUser = userService.getAllUser();

        return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.OK);

    }

    /**
     * @apiNote This method is for Getting User By ID
     * @param userId
     * @return
     */

    //user by id
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {

        logger.info("Entering the request for get single user details with userId:{}",userId);
        UserDto userById = userService.getUserById(userId);

        return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for Getting User By Email
     * @param email
     * @return
     */

    //user by email

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        logger.info("Entering the request to Get Single User details with EmailId:{}",email);
        UserDto byEmail = userService.getUserByEmail(email);

        return new ResponseEntity<UserDto>(byEmail, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for Searching User by Keyword
     * @param keyword
     * @return
     */

    //search keyword

    @GetMapping("/searchKeyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        logger.info("Entering Request for get the single user details with keyword:{}",keyword);
        List<UserDto> user = userService.searchUser(keyword);

        return new ResponseEntity<List<UserDto>>(user, HttpStatus.OK);
    }


}