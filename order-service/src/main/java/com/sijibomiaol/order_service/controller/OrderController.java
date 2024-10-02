package com.sijibomiaol.order_service.controller;

import com.sijibomiaol.order_service.dto.OrderRequest;
import com.sijibomiaol.order_service.entity.OrderItem;
import com.sijibomiaol.order_service.entity.OrderProducts;
import com.sijibomiaol.order_service.service.OrderProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    OrderProductsService orderProductsService;

    @PostMapping
    public ResponseEntity<OrderProducts> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            OrderProducts orderProducts = orderProductsService.createOrderProduct(orderRequest);
            return new ResponseEntity<>(orderProducts, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<OrderProducts> getOrderProduct(@PathVariable UUID orderId) {
        try {
            OrderProducts orderProducts = orderProductsService.getOrderProduct(orderId);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @PutMapping
    ResponseEntity<OrderProducts> cancelOrderProduct(@PathVariable UUID orderId) {
        try {
            OrderProducts orderProducts = orderProductsService.cancelOrderProduct(orderId);
            return new ResponseEntity<>(orderProducts, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
