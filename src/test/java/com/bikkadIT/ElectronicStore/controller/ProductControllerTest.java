package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.entities.Product;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.services.FileService;
import com.bikkadIT.ElectronicStore.services.ProductService;
import com.bikkadIT.ElectronicStore.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    public void init() {

        product = Product.builder()
                .title("Samsung Galaxy 11")
                .price(30000)
                .discountedPrice(28000)
                .quantity(20)
                .stock(true)
                .live(true)
                .description("This is Samsung Phone")
                .addedDate(new Date())
                .build();
    }


    @Test
    void createProductTest() throws Exception {

        ProductDto dto = mapper.map(product, ProductDto.class);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }


    @Test
    void updateProductTest() throws Exception {

        String productId = "2123";

        ProductDto dto = mapper.map(product, ProductDto.class);

        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }



    @Test
    void getSingleProduct() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getLiveProducts() {
    }

    @Test
    void searchProducts() {
    }

    @Test
    void uploadProductImage() {
    }

    @Test
    void serveProductImage() {
    }

    private String convertObjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);   // object ki values ko string me write karke return karega
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}