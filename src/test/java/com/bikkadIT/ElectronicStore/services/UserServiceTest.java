package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;   //userService k andar jo repository h usko @MockBean Karna padta h i.e. (userRepository) for getting fake data..not actual

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
        Assertions.assertEquals("Manish", user1.getName());
    }

    // update user

    @Test
    public void updateUserTest() {

        String userId = "dcdfvrffv";

        UserDto userDto = UserDto.builder()
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
        Assertions.assertEquals(userDto.getName(), updateUser.getName(), "Name is not validated");

    }

    // delete user
    @Test
    public void deleteUserTest() {

        String userId = "userAbc";

        Mockito.when(userRepository.findById("userAbc")).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
        //return me void mil raha h esliye hamne Mockito.verify karne k liye userRepository delete(user) method execute ho raha h
        // actual me image honi chahiye otherwise NosuchFileException ayega

    }

    // get All user
    @Test
    public void getAllUserTest() {

        User user1 = User.builder()
                .name("Rudra")
                .email("mn@gmail.com")
                .about("This is create user testing")
                .gender("Male")
                .imageName("m.png")
                .password("abcd")
                .build();

        User user2 = User.builder()
                .name("Bhavesh")
                .email("mn@gmail.com")
                .about("This is create user testing")
                .gender("Male")
                .imageName("m.png")
                .password("abcd")
                .build();


        List<User> userList = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(userList);

        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1, 1, "name", "asc");

        Assertions.assertEquals(3, allUser.getContent().size());
    }


    // get user by id
    @Test
    public void getUserByIdTest() {

        String userID = "userID";
        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));

        //actual call of service method
        UserDto userDto = userService.getUserById(userID);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "Name not matched");
        //expected result,  actual result   ,   if not match then message

        System.out.println(userDto.getName()); //expected
        System.out.println(user.getName()); //actual


    }

    // get user by email

    @Test
    public void getUserByEmailTest() {

        String email = "m@g.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto userdto = userService.getUserByEmail(email);

        Assertions.assertNotNull(userdto);
        Assertions.assertEquals(user.getEmail(), userdto.getEmail(), "Email not matched");

        System.out.println(userdto.getEmail());
        System.out.println(user.getEmail());
    }

    // search user by keyword
    @Test
    public void searchUserTest() {

        User user1 = User.builder()
                .name("Harshal")
                .email("mn@gmail.com")
                .about("This is create user testing")
                .gender("Male")
                .imageName("m.png")
                .password("abcd")
                .build();

        User user2 = User.builder()
                .name("Pavan")
                .email("mn@gmail.com")
                .about("This is create user testing")
                .gender("Male")
                .imageName("m.png")
                .password("abcd")
                .build();

        List<User> users = Arrays.asList(user1, user2);

        String keyword = "manish";

        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(users);

        List<UserDto> usersDto = userService.searchUser(keyword);

        Assertions.assertNotNull(usersDto);
        Assertions.assertEquals(2, users.size(), "Size not matched");


    }


}
