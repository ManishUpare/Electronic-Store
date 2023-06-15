package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.entities.Category;
import com.bikkadIT.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.PageHelper;
import com.bikkadIT.ElectronicStore.repositories.CategoryRepository;
import com.bikkadIT.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;


    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        logger.info("Initiating Category Service for the save category details");

        // creating categoryId randomly

        String categoryID = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryID);

        Category category = mapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);

        logger.info("Completed Category Service call for the save category details");
        return mapper.map(saveCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        logger.info("Entering Category Service call for the update the Category details with:{}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);

        logger.info("Completed Category Service call for the update the Category details with:{}", categoryId);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {

        logger.info("Initiating Category Service call for the delete the category details with:{}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));

        //  delete Category profile image
        //  images/category/abc.png  -->fullPath

        String fullPath = imagePath + category.getCoverImage();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            logger.info("Category cover Image not Found in folder");
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Completed Category Service call for the delete the user details with:{}", categoryId);
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        logger.info("Initiating Category Service call for the get all users");

        //returns true if the strings are equal, and false if not.
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> response = PageHelper.getPageResponse(page, CategoryDto.class);

        logger.info("Completing Category Service call for get All users");
        return response;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {

        logger.info("Initiating dao call for the get the single user details with:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + categoryId));

        logger.info("Initiating dao call for the get the single user details with:{}", categoryId);
        return mapper.map(category,CategoryDto.class);
    }
}
