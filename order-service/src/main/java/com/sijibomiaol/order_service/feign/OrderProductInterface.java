//package com.sijibomiaol.order_service.feign;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.UUID;
//@Configuration
//@FeignClient("PRODUCT-SERVICE")
//public interface OrderProductInterface {
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable UUID id);
//}
