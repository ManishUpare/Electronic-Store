package com.bikkadIT.ElectronicStore.repositories;

import com.bikkadIT.ElectronicStore.entities.Product;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ProductRepository extends JpaRepository<Product, String> {


    //search
   Page<Product> findByTitleContaining(String subTitle,Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    //other
    //customFinder Methods
    //query method
}
