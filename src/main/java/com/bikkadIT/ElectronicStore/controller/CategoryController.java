package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.CategoryDto;
import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.payloads.ImageResponse;
import com.bikkadIT.ElectronicStore.services.CategoryService;
import com.bikkadIT.ElectronicStore.services.FileService;
import com.bikkadIT.ElectronicStore.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductService productService;

    @Value("${product.image.path}")    //set KEY path from application.properties
    private String imageUploadPath;


    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    /**
     * @Author Manish Upare
     * @param categoryDto
     * @apiNote This method is for creating category details
     */

    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        logger.info("Entering for save the Category Details");
        CategoryDto category = categoryService.createCategory(categoryDto);

        logger.info("Entering for save the Category Details");
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);

    }

    /**
     * @apiNote This method is for updating category Details
     * @param categoryDto
     * @param categoryId
     * @return
     */

    //update

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid
                                                      @RequestBody CategoryDto categoryDto,
                                                      @PathVariable String categoryId) {

        logger.info("Entering for update the Category Details");
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        logger.info("Entering for update the Category Details");
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);

    }
    //delete

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){

        logger.info("Entering the request for delete category details with userId:{}", categoryId);
        categoryService.deleteCategory(categoryId);

//        ApiResponse ap= ApiResponse
//                .builder()
//                .message("Category Deleted Successfully !!")
//                .status(HttpStatus.OK)
//                .success(true)
//                .build();

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Category Deleted Successfully !!");
        apiResponse.setStatus(HttpStatus.OK);

        logger.info("Completed the request for delete category details with userId:{}", categoryId);

        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
    //get all

    /**
     * @apiNote This method is for getting all category
     * @param pageNumber
     * @param pagSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @GetMapping("/allCategory")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pagSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir
            ){
        logger.info("Entering the request for get all category data");
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pagSize, sortBy, sortDir);

        return new ResponseEntity<PageableResponse<CategoryDto>>(allCategory,HttpStatus.OK);

    }
    // get single category By Id

    /**
     * @apiNote This method is for get Category By ID
     * @param categoryId
     * @return
     */

    @GetMapping("/getCategory/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){

        logger.info("Entering the request for get single user details with userId:{}", categoryId);
        CategoryDto singleCategoryById = categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<CategoryDto>(singleCategoryById,HttpStatus.OK);
    }

    /**
     * @apiNote This method is for uploading cover image of category
     * @param image
     * @param categoryId
     * @throws IOException
     */

    // upload Category image
    @PostMapping("/imageUpload/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestPart("coverImage") MultipartFile image,
                                                         @PathVariable String categoryId) throws IOException {

        logger.info("Entering the request to Upload Image in the Category with Category ID: {} ", categoryId);

        CategoryDto category = categoryService.getSingleCategory(categoryId);
        String imageName = fileService.uploadFile(image, imageUploadPath);

        category.setCoverImage(imageName);

        CategoryDto categoryDto = categoryService.updateCategory(category, categoryId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .message("Category Image Upload Successfully !!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        logger.info("Completed the request of Uploading Image in the Category with Category ID: {} ", categoryId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

    }

    /**
     * @apiNote This method is for download image
     * @param categoryId
     * @param response
     * @throws IOException
     */
    // serve Category image
    @GetMapping(value = "/image/{categoryId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response)
            throws IOException {

        logger.info("Entering the Category request to Serve the Cover Image on the Server : {}",categoryId);

        CategoryDto category = categoryService.getSingleCategory(categoryId);

        logger.info("Category cover image name : {} ", category.getCoverImage());

        InputStream resource = fileService.getResource(imageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

        logger.info("Completed the Category request after Serving the Cover Image on the Server : {}",categoryId);

    }

    //create product with category

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable("categoryId") String categoryId,
                                                                @RequestBody ProductDto productDto){

        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);

        return new ResponseEntity<ProductDto>(productWithCategory,HttpStatus.CREATED);


    }

}