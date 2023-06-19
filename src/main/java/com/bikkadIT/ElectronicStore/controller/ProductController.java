package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * @Author Manish Upare
     * @apiNote This method is for save product details
     * @param productDto
     */

    //create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Entering for save the product Details");
        ProductDto product = productService.createProduct(productDto);
        log.info("Completed for save the product Details");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    //update

    /**
     * @apiNote This method is for update the product details
     * @param productDto
     * @param productId
     * @return
     */

    @PostMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,
                                                    @PathVariable String productId) {

        log.info("Entering for update the product Details with productId:{}", productId);
        ProductDto productDto1 = productService.updateProduct(productDto, productId);

        log.info("Completed for update the product Details with productId:{}", productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for delete the product details
     * @param productId
     * @return
     */
    //delete
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {

        log.info("Entering the request for delete product details with productId:{}", productId);

        ApiResponse apiResponse = ApiResponse.builder()
                .message(AppConstant.USER_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        log.info("Completed the request for delete product details with productId:{}", productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for get the single product
     * @param productId
     * @return
     */
    //get single
    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        log.info("Entering the request for get single product details with productId:{}", productId);
        ProductDto singleProduct = productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    /**
     * @apiNote This methos is for getting all products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    // get All
    @GetMapping("/getAllProducts")
    public ResponseEntity<PageableResponse<ProductDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        log.info("Entering the request for get all product data");
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for getting live products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    //live product
    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>> getLiveProducts(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                        @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                        @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        log.info("Entering Request for getting live products details");
        PageableResponse<ProductDto> allProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }


    /**
     * @apiNote This method is for searching products
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    // search

    @GetMapping("/get/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProducts(@PathVariable String query,
                                                                       @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                       @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                       @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                       @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        log.info("Entering Request for get the single product details with query:{}", query);
        PageableResponse<ProductDto> allProduct = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }
}
