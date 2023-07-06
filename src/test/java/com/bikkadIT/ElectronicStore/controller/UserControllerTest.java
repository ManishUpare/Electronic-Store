package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private User user;

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

    @Test
    void createUserTest() throws Exception {
        // URL -->  /user + POST+ user data as Json
        //   return data as Json+status creted

        UserDto dto = mapper.map(user, UserDto.class);

//   when we call createUser on userService with any object then return dto      controller kbhibhi service ko hi call karta h
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //URL request karne k liye Mockmvc it has methods for performing operations
        //actual request for URL

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/create")
                                .contentType(MediaType.APPLICATION_JSON)     // sending Json data
                                .content(convertObjectToJsonString(user))    // actual kya content bhejna h
                                .accept(MediaType.APPLICATION_JSON))         // accept data in

                .andDo(print())
                .andExpect(status().isCreated())             // Expectation from this URL->1.Status 2. data property
                .andExpect(jsonPath("$.name").exists()); //data property exist with name or not

    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);   // object ki values ko string me write karke return karega
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void searchUser() {
    }

    @Test
    void uploadUserImage() {
    }

    @Test
    void serveUserImage() {
    }
}