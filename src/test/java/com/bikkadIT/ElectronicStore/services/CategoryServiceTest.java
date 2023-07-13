package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.entities.Category;
import com.bikkadIT.ElectronicStore.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;

    @BeforeEach
    public void init(){

     category = Category.builder()
                .title("Mobiles")
                .description("This category contains Mobiles")
                .coverImage("mobile.png")
                .build();
    }

    @Test
    void createCategory_Test() {

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        //actual
        CategoryDto dto = categoryService.createCategory(mapper.map(category, CategoryDto.class));

        System.out.println(dto.getTitle());

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Mobiles",dto.getTitle());

    }

    @Test
    void updateCategory_Test() {

        CategoryDto categoryDto=CategoryDto.builder().title("New Mobiles").description("This is categoryDto").coverImage("newMob.png").build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto cDto = categoryService.updateCategory(categoryDto, Mockito.anyString());

        System.out.println(cDto.getTitle());
        System.out.println(cDto.getDescription());


        Assertions.assertNotNull(categoryDto);

        Assertions.assertEquals(cDto.getTitle(), category.getTitle(), "Title not matched");


    }

    @Test
    void deleteCategory() {

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));

        categoryService.deleteCategory(Mockito.anyString());

        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);



    }

    @Test
    void getAllCategory() {
    }

    @Test
    void getSingleCategory() {
    }
}