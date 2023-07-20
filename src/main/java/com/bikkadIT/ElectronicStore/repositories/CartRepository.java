package com.bikkadIT.ElectronicStore.repositories;

import com.bikkadIT.ElectronicStore.entities.Cart;
import com.bikkadIT.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

   Optional<Cart> findByUser(User user);
}
