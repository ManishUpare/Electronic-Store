package com.bikkadIT.ElectronicStore.repositories;

import com.bikkadIT.ElectronicStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    //search
    List<Product> findByTitleContaining(String subTitle);
    List<Product> findByLiveTrue();
    //other
    //customFinder Methods
    //query method
}
