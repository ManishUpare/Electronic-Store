package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    //delete
    void deleteCategory(String categoryId);

    //get All
    PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single category details
    CategoryDto getSingleCategory(String categoryId);
}
