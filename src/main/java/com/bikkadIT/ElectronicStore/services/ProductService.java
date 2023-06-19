package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;

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
   List<ProductDto> getAllProduct();

    //get all:Live
    List<ProductDto> getAllLiveProduct();

    //search product
    List<ProductDto> searchByTitle(String subTitle);

    //other methods
}
