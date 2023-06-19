package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.dtos.ProductDto;
import com.bikkadIT.ElectronicStore.entities.Product;
import com.bikkadIT.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.PageHelper;
import com.bikkadIT.ElectronicStore.repositories.ProductRepository;
import com.bikkadIT.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Initiating dao call for the save product details");
        Product product = mapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        log.info("Completed dao call for the save product details");
        return mapper.map(saveProduct, ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        Product newProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));

        newProduct.setDescription(productDto.getDescription());
        newProduct.setTitle(productDto.getTitle());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setDiscountedPrice(productDto.getDiscountedPrice());
        newProduct.setQuantity(productDto.getQuantity());
        newProduct.setIsStock(productDto.getIsStock());
        newProduct.setIsLive(productDto.getIsLive());

        Product updatedProduct = productRepository.save(newProduct);

        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));

        productRepository.delete(product);

    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));

        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort= (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy).ascending()):(Sort.by(sortBy).descending());

//        if (sortDir.equalsIgnoreCase("asc")) {
//            sort = Sort.by(sortBy).ascending();
//        } else {
//            sort = Sort.by(sortBy).descending();
//        }

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = productRepository.findAll(pageable);
        return PageHelper.getPageResponse(all, ProductDto.class);

    }

    @Override
    public  PageableResponse<ProductDto> getAllLiveProduct(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        Sort sort= (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy).ascending()):(Sort.by(sortBy).descending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return PageHelper.getPageResponse(page, ProductDto.class);
    }

    @Override
    public  PageableResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort= (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy).ascending()):(Sort.by(sortBy).descending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return PageHelper.getPageResponse(page, ProductDto.class);
    }
}
