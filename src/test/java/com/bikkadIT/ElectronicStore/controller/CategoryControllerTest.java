package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.entities.Category;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryController;

    Category category;

    @BeforeEach
    public void init() {

        category = Category.builder()
                .title("Mobiles")
                .description("This is Category Controller_Test")
                .coverImage("mob.png")
                .build();
    }

    @Test
    void createCategory_Test() throws Exception {

        // URL -->  /category/create + POST request + category data as Json
        //   return data as Json+status created

        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/category/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }


    @Test
    void updateCategory_Test() throws Exception {

        //URL   category/update/{categoryId}

        String cId = "123";

        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/category/update/" + cId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void deleteCategory_Test() throws Exception {

        //URL   /category/delete/{categoryId}

        String cId = "123";

        Mockito.doNothing().when(categoryService).deleteCategory(cId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/delete/" + cId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllCategory_Test() throws Exception {

        CategoryDto c1 = CategoryDto.builder().title("Mobiles").description("This is Mobiles category").build();
        CategoryDto c2 = CategoryDto.builder().title("TV").description("This is Mobiles category").build();
        CategoryDto c3 = CategoryDto.builder().title("AC").description("This is Mobiles category").build();


        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(c1,c2,c3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalElements(100);
        pageableResponse.setTotalPages(1000);

        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/allCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getCategoryById_Test() throws Exception {

        String cId = "123";

        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.getSingleCategory(cId)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/getCategory/" + cId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void uploadCategoryImage() {
    }

    @Test
    void serveCategoryImage() {
    }

    @Test
    void createProductWithCategory() {
    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);   // object ki values ko string me write karke return karega
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}