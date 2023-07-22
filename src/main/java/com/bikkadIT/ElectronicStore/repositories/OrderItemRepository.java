package com.bikkadIT.ElectronicStore.repositories;

import com.bikkadIT.ElectronicStore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
