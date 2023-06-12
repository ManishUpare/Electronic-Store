package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.PageHelper;
import com.bikkadIT.ElectronicStore.repositories.UserRepository;
import com.bikkadIT.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserDto createUser(UserDto userDto) {

        //generate unique id in String format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        logger.info("Initiating dao call for the save user details");

        //Dto to entity
        User user = mapper.map(userDto, User.class);

        User saveUser = userRepository.save(user);

        logger.info("Completing dao call for the user details");

        //entity to Dto
        return mapper.map(saveUser, UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        logger.info("Initiating dao call for the update the user details with:{}", userId);

        User newUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));

        newUser.setName(userDto.getName());
        newUser.setPassword(userDto.getPassword());
        newUser.setGender(userDto.getGender());
        newUser.setAbout(userDto.getAbout());
        newUser.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(newUser);

        UserDto Dtos = mapper.map(updatedUser, UserDto.class);

        logger.info("Completing dao call for the update the user details with:{}", userId);

        return Dtos;
    }

    @Override
    public void deleteUser(String userId) {

        logger.info("Initiating dao call for the delete the user details with:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));

        //  delete user profile image
        //  images/user/abc.png  -->fullPath

        String fullPath = imagePath + user.getImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            logger.info("User Image not Found in folder");
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        logger.info("Completing dao call for the update the user details with:{}", userId);
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        logger.info("Initiating dao call for the get all users");

        //returns true if the strings are equal, and false if not.
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = this.userRepository.findAll(pageable);

        PageableResponse<UserDto> response = PageHelper.getPageResponse(page, UserDto.class);

        logger.info("Completing dao call for get All users");
        return response;

    }

    @Override
    public UserDto getUserById(String userId) {

        logger.info("Initiating dao call for the get the single user details with:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));

        UserDto dto = mapper.map(user, UserDto.class);

        logger.info("Completed dao call for the get the single user details with:{}", userId);
        return dto;
    }

    @Override
    public UserDto getUserByEmail(String email) {

        logger.info("Initiating dao call for the get the single user details with:{}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + email));

        logger.info("Completed dao call for the get the single user details with:{}", email);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        logger.info("Initiating dao call for the get the user details with:{}", keyword);
        List<User> users = userRepository.findByNameContaining(keyword);

        List<UserDto> list = users.stream().map((m) -> mapper.map(m, UserDto.class)).collect(Collectors.toList());

        logger.info("Completed dao call for the get the user details with:{}", keyword);
        return list;

    }
}
