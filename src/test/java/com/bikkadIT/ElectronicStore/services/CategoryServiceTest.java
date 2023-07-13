package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.entities.Category;
import com.bikkadIT.ElectronicStore.entities.Product;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
    public void init() {

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
        Assertions.assertEquals("Mobiles", dto.getTitle());

    }

    @Test
    void updateCategory_Test() {

        CategoryDto categoryDto = CategoryDto.builder().title("New Mobiles").description("This is categoryDto").coverImage("newMob.png").build();

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

        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);

    }

    @Test
    void getAllCategory() {

        Category c1 = Category.builder()
                .title("Washing Machine")
                .description("This category contains Washing Machine")
                .coverImage("wash.png")
                .build();

        Category c2 = Category.builder()
                .title("TV")
                .description("This category contains TV")
                .coverImage("tv.png")
                .build();


        List<Category> list = Arrays.asList(category,c1,c2);

        Page<Category> page = new PageImpl<>(list);

        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 1, "title", "asc");

        Assertions.assertEquals(3,allCategory.getContent().size());

    }


}