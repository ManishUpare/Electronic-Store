package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.entities.Category;
import com.bikkadIT.ElectronicStore.entities.Product;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.repositories.ProductRepository;
import com.bikkadIT.ElectronicStore.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;   //productService k andar jo repository h usko @MockBean Karna padta h i.e. (productRepository) for getting fake data..not actual

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    public void init(){

       product= Product.builder()
                .title("Iphone 11")
                .price(20000)
                .discountedPrice(18000)
                .quantity(25)
                .stock(true)
                .live(true)
                .description("Phone with 6gb Ram 128Gb internal")
                .addedDate(new Date())
                .build();

    }

    @Test
    void createProductTest() {

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        //actual result
        ProductDto productDto = productService.createProduct(mapper.map(product, ProductDto.class));

        System.out.println(productDto.getTitle());
        System.out.println(productDto.getAddedDate());

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals("Iphone 11",productDto.getTitle());

    }

    @Test
    void updateProductTest() {

        String productId="product";

        ProductDto productDto=ProductDto.builder()
                .title("Samsung s22")
                .price(50000)
                .discountedPrice(45000)
                .quantity(25)
                .stock(true)
                .live(true)
                .description("Phone with 6gb Ram 128Gb internal")
                .addedDate(new Date())
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto updatedProduct = productService.updateProduct(productDto, productId);

        System.out.println(updatedProduct.getTitle());
        System.out.println(updatedProduct.getDescription());

        Assertions.assertNotNull(productDto);

        Assertions.assertEquals(updatedProduct.getTitle(),product.getTitle(),"Title not matched");


    }

    @Test
    void deleteProductTest() {

        String pId="productId";

        Mockito.when(productRepository.findById(pId)).thenReturn(Optional.of(product));

        productService.deleteProduct(pId);

        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
        //return me void mil raha h esliye hamne Mockito.verify karne k liye productRepository delete(user) method execute ho raha h
        // actual me image honi chahiye otherwise NosuchFileException ayega

    }

    @Test
    void getSingleProduct() {
    }

    @Test
    void getAllProduct() {
    }

    @Test
    void getAllLiveProduct() {
    }

    @Test
    void searchByTitle() {
    }

    @Test
    void createWithCategory() {
    }
}