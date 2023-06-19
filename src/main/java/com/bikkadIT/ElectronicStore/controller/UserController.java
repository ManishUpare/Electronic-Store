package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.payloads.ImageResponse;
import com.bikkadIT.ElectronicStore.services.FileService;
import com.bikkadIT.ElectronicStore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @Author Manish Upare
     * @param userDto
     * @apiNote This method is for Creating User
     */

    Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        logger.info("Entering for save the User Details");
        UserDto createUser = userService.createUser(userDto);

        logger.info("Completed the request for create user Details");
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);

    }

    /**
     * @param userDto
     * @param userId
     * @return
     * @apiNote This method is for Updating User
     */


    //update
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable String userId) {

        logger.info("Entering the request for update user details with userId:{}", userId);
        UserDto userDto1 = userService.updateUser(userDto, userId);

        logger.info("Completed the request for update user details with userId:{}", userId);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @apiNote This method is for Deleting User
     */

    //delete
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {

        logger.info("Entering the request for delete user details with userId:{}", userId);
        userService.deleteUser(userId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(AppConstant.USER_DELETE);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        logger.info("Completed the request for delete details with userId:{}", userId);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

    /**
     * @apiNote ThiS method is for Getting All User
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    //getAllUser
    @GetMapping("/AllUser")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        logger.info("Entering the request for get all user data");
        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PageableResponse<UserDto>>(allUser, HttpStatus.OK);

    }

    /**
     * @param userId
     * @return
     * @apiNote This method is for Getting User By ID
     */

    //user by id
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {

        logger.info("Entering the request for get single user details with userId:{}", userId);
        UserDto userById = userService.getUserById(userId);

        return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @apiNote This method is for Getting User By Email
     */

    //user by email
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        logger.info("Entering the request to Get Single User details with EmailId:{}", email);
        UserDto byEmail = userService.getUserByEmail(email);

        return new ResponseEntity<UserDto>(byEmail, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @apiNote This method is for Searching User by Keyword
     */

    //search keyword
    @GetMapping("/searchKeyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        logger.info("Entering Request for get the single user details with keyword:{}", keyword);
        List<UserDto> user = userService.searchUser(keyword);

        return new ResponseEntity<List<UserDto>>(user, HttpStatus.OK);
    }

    /**
     * @param image
     * @param userId
     * @return
     * @throws IOException
     * @apiNote This method is for uploading User Image
     */

    // upload User image
    @PostMapping("/imageUpload/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestPart("userImage") MultipartFile image,
                                                         @PathVariable String userId) throws IOException {

        logger.info("Entering the request to Upload Image in the User with User ID: {} ", userId);

        UserDto user = userService.getUserById(userId);
        String imageName = fileService.uploadFile(image, imageUploadPath);

        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse
                .builder().imageName(imageName).message("Image Upload Successfully !!").success(true).status(HttpStatus.CREATED).build();

        logger.info("Completed the request of Uploading Image in the User with User ID: {} ", userId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

    }

    /**
     * @param userId
     * @param response
     * @throws IOException
     * @apiNote This method is for getting User Image
     */

    // serve user image
    @GetMapping(value = "/user/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response)
            throws IOException {

        logger.info("Entering the request to Serve the Image on the Server : {}",userId);

        UserDto user = this.userService.getUserById(userId);
        logger.info("User image name : {} ", user.getImageName());

        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

        logger.info("Completed the request after Serving the Image on the Server : {}",userId);

    }


}