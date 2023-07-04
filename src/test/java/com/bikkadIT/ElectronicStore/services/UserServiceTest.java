package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

   @Autowired
    private UserService userService;   //userService k andar jo repository h usko @MockBean Karna padta h for getting fake data..not actual

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    public void init() {

        user = User.builder()
                .name("Manish")
                .email("mn@gmail.com")
                .about("This is create user testing")
                .gender("Male")
                .imageName("m.png")
                .password("abcd")
                .build();
    }

    //create user

    @Test
    public void createUserTest() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));

        System.out.println(user1.getName());

        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Manish",user1.getName());
    }

    // update user

    @Test
    public void updateUserTest(){

        String userId="dcdfvrffv";

       UserDto userDto= UserDto.builder()
                .name("Manohar")
                .about("This is update user testing")
                .gender("Male")
                .imageName("mano.png")
                .password("harrier")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userId);

        System.out.println(updateUser.getName());
        System.out.println(updateUser.getImageName());

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name is not validated");

    }
}
