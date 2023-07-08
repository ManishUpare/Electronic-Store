package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;

import java.util.Arrays;

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
        // URL -->  /users/create + POST request + user data as Json
        //   return data as Json+status created

        UserDto dto = mapper.map(user, UserDto.class);

//   when we call createUser on userService with any object then return dto      controller kbhibhi service ko hi call karta h
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //URL request karne k liye Mockmvc, it has methods for performing operations
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

    @Test
    void updateUserTest() throws Exception {
        // URL -->  /users/update/{userId} + PUT request + user data as Json
        //   return data as Json+status creted

        String userId = "123";

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        //Mockito.when(userService.updateUser(dto,userId)).thenReturn(dto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users/update" + userId)
                                //  .header(HttpHeaders.AUTHORIZATION,"Bearer token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    void getAllUsersTest() throws Exception {

        UserDto u1 = UserDto.builder().name("sagar").email("sagar@gmail.com").password("abcd").gender("Male").about("Tester").build();
        UserDto u2 = UserDto.builder().name("akash").email("akash@gmail.com").password("abcd").gender("Male").about("Tester").build();
        UserDto u3 = UserDto.builder().name("munna").email("munna@gmail.com").password("abcd").gender("Male").about("Tester").build();
        UserDto u4 = UserDto.builder().name("khilesh").email("khilesh@gmail.com").password("abcd").gender("Male").about("Tester").build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(u1, u2, u3, u4));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalElements(100);
        pageableResponse.setTotalPages(1000);

        Mockito.when(userService.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/AllUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

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
    void deleteUserTest() throws Exception {

        String userId = "123";
        //Mockito.when(userService.deleteUser(userId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void getUserByIdTest() throws Exception {

        String userId = "123";

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.getUserById(userId)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/getUser/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void getUserByEmailTest() throws Exception {

        String email="mail";

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.getUserByEmail(email)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/getByEmail/"+email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

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