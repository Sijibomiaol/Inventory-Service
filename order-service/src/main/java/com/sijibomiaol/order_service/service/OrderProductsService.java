package com.sijibomiaol.order_service.service;

import com.sijibomiaol.order_service.dto.OrderRequest;
import com.sijibomiaol.order_service.entity.OrderProducts;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderProductsService {

    @Transactional
    OrderProducts createOrderProduct(OrderRequest orderRequest);

    OrderProducts getOrderProduct(UUID orderId) throws  Exception;



    @Transactional
    OrderProducts cancelOrderProduct(UUID orderId) throws Exception;
}
