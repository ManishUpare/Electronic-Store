package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.dtos.UserDto;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.payloads.ImageResponse;
import com.bikkadIT.ElectronicStore.services.FileService;
import com.bikkadIT.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")     //set KEY path from application.properties
    private String imageUploadPath;

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

    @PutMapping("/update/{productId}")
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
        productService.deleteProduct(productId);

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
     * @apiNote This method is for getting all products
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

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProducts(@PathVariable String query,
                                                                       @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                       @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                       @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
                                                                       @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        log.info("Entering Request for get the single product details with query:{}", query);
        PageableResponse<ProductDto> allProduct = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for upload product image
     * @param image
     * @param productId
     * @return
     * @throws IOException
     */
    // upload Product image
    @PostMapping("/imageUpload/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestPart("productImage") MultipartFile image,
                                                         @PathVariable String productId) throws IOException {

        log.info("Entering the request to Upload Image in the Product with Product ID: {} ", productId);

        ProductDto product = productService.getSingleProduct(productId);
        String imageName = fileService.uploadFile(image, imageUploadPath);

        product.setProductImageName(imageName);
        ProductDto productDto = productService.updateProduct(product, productId);

        ImageResponse imageResponse = ImageResponse
                .builder().imageName(imageName).message(" Product Image Upload Successfully !!").success(true).status(HttpStatus.CREATED).build();

        log.info("Completed the request of Upload Image in the Product with Product ID: {} ", productId);

        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

    }

    /**
     * @apiNote This method is for download product image
     * @param productId
     * @param response
     * @throws IOException
     */

    // serve product image
    @GetMapping(value = "/image/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response)
            throws IOException {

        log.info("Entering the request to Serve the Image on the Server : {}",productId);

        ProductDto product = productService.getSingleProduct(productId);
        log.info("Product image name : {} ", product.getProductImageName());

        InputStream resource = fileService.getResource(imageUploadPath, product.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

        log.info("Completed the request after Serving the Image on the Server : {}",productId);

    }
}
