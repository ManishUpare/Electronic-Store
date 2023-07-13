package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.entities.Product;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
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
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


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
    void deleteProductTest() throws Exception {

        String productId = "234";

        Mockito.doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(productService, Mockito.times(1)).deleteProduct(productId);

    }

    @Test
    void getSingleProductTest() throws Exception {

        String productId = "2311";

        ProductDto dto = mapper.map(product, ProductDto.class);

        Mockito.when(productService.getSingleProduct(productId)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/getProduct/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void getAllTest() throws Exception {

        ProductDto p1 = ProductDto.builder().title("IPhone").description("This is Iphone Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p2 = ProductDto.builder().title("IPhone").description("This is Iphone Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p3 = ProductDto.builder().title("IPhone").description("This is Iphone Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p4 = ProductDto.builder().title("IPhone").description("This is Iphone Mobiles").price(80000).discountedPrice(79000).build();

        List<ProductDto> list = Arrays.asList(p1, p2, p3, p4);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(list);
        pageableResponse.setTotalElements(10);
        pageableResponse.setPageSize(100);
        pageableResponse.setTotalPages(1000);

        Mockito.when(productService.getAllProduct(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);


        mockMvc.perform(MockMvcRequestBuilders.get("/products/getAllProducts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getLiveProducts() throws Exception {

        ProductDto p1 = ProductDto.builder().title("IPhone").description("This is Iphone Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p2 = ProductDto.builder().title("Samsung").description("This is Samsung Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p3 = ProductDto.builder().title("Vivo").description("This is vivo Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p4 = ProductDto.builder().title("OPPO").description("This is oppo Mobiles").price(80000).discountedPrice(79000).build();

        List<ProductDto> liveList = Arrays.asList(p1, p2, p3, p4);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(liveList);
        pageableResponse.setTotalElements(1000);
        pageableResponse.setTotalPages(1000);
        pageableResponse.setPageSize(50);
        pageableResponse.setLastPage(false);

        Mockito.when(productService.getAllLiveProduct(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/getAllLive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void searchProductsTest() throws Exception {

        ProductDto p1 = ProductDto.builder().title("IPhone").description("This is Iphone Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p2 = ProductDto.builder().title("Samsung").description("This is Samsung Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p3 = ProductDto.builder().title("Vivo").description("This is vivo Mobiles").price(80000).discountedPrice(79000).build();
        ProductDto p4 = ProductDto.builder().title("OPPO").description("This is oppo Mobiles").price(80000).discountedPrice(79000).build();

        List<ProductDto> liveList = Arrays.asList(p1, p2, p3, p4);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(liveList);
        pageableResponse.setTotalElements(1000);
        pageableResponse.setTotalPages(1000);
        pageableResponse.setPageSize(50);
        pageableResponse.setLastPage(false);

        Mockito.when(productService.searchByTitle(Mockito.anyString(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        String query="123";

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/"+query)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product)))
                .andDo(print())
                .andExpect(status().isOk());
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