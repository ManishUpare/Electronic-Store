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

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        //generating random ID
        String Id = UUID.randomUUID().toString();
        product.setProductId(Id);

        //Added date
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);
        log.info("Completed dao call for the save product details");
        return mapper.map(saveProduct, ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        log.info("Initiating dao call for the update the product details with:{}", productId);

        Product newProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));

        newProduct.setDescription(productDto.getDescription());
        newProduct.setTitle(productDto.getTitle());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setDiscountedPrice(productDto.getDiscountedPrice());
        newProduct.setQuantity(productDto.getQuantity());
        newProduct.setLive(productDto.isLive());
        newProduct.setStock(productDto.isStock());
        newProduct.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(newProduct);

        log.info("Completed dao call for the update the product details with:{}", productId);

        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        log.info("Initiating dao call for the delete the product details with:{}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));
        log.info("Completing dao call for the delete the product details with:{}", productId);
        productRepository.delete(product);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        log.info("Initiating dao call for the getting single product details with:{}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));
        log.info("Completed dao call for the getting single product details with:{}", productId);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiating dao call for the get all products");

        Sort sort= (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy).ascending()):(Sort.by(sortBy).descending());

//        if (sortDir.equalsIgnoreCase("asc")) {
//            sort = Sort.by(sortBy).ascending();
//        } else {
//            sort = Sort.by(sortBy).descending();
//        }

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = productRepository.findAll(pageable);
        log.info("Completed dao call for the get all products");

        return PageHelper.getPageResponse(all, ProductDto.class);

    }

    @Override
    public  PageableResponse<ProductDto> getAllLiveProduct(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        log.info("Initiating dao call for the get all live product details");
        Sort sort= (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy).ascending()):(Sort.by(sortBy).descending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
       // Page<Product> page = productRepository.findByLiveTrue(pageable);
        Page<Product> live = productRepository.findByLiveTrue(pageable);
        log.info("Completed dao call for the get all live product details");
        return PageHelper.getPageResponse(live, ProductDto.class);
    }

    @Override
    public  PageableResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        log.info("Initiating dao call for the get the product details with:{}", subTitle);

        Sort sort= (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy).ascending()):(Sort.by(sortBy).descending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        log.info("Completed dao call for the get the product details with:{}", subTitle);
        return PageHelper.getPageResponse(page, ProductDto.class);
    }
}
