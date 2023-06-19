package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;

import java.util.List;

public interface ProductService {

    //create
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProduct(ProductDto productDto,String productId);

    //delete
    void deleteProduct(String productId);

    //get single
    ProductDto getSingleProduct(String productId);

    //get all
   PageableResponse<ProductDto> getAllProduct(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get all:Live
    PageableResponse<ProductDto> getAllLiveProduct(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);


    //other methods
}
