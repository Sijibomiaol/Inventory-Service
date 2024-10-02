package com.sijibomiaol.order_service.repository;

import com.sijibomiaol.order_service.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProducts, UUID> {

}
