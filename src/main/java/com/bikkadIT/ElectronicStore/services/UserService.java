package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    //other user specific features

}
