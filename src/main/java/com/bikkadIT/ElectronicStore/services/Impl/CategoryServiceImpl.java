package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.entities.Category;
import com.bikkadIT.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.repositories.CategoryRepository;
import com.bikkadIT.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        logger.info("Initiating dao call for the save category details");

        Category category = mapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);

        logger.info("Completed dao call for the save category details");
        return mapper.map(saveCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        logger.info("Entering dao call for the update the Category details with:{}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);

        logger.info("Completed dao call for the update the Category details with:{}", categoryId);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {

    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory() {
        return null;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        return null;
    }
}
