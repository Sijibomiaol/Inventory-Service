package com.sijibomiaol.order_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sijibomiaol.order_service.Enum.OrderStatus;
import com.sijibomiaol.order_service.dto.ApiResponse;
import com.sijibomiaol.order_service.dto.OrderItemDto;
import com.sijibomiaol.order_service.dto.OrderRequest;
import com.sijibomiaol.order_service.dto.ProductResponse;
import com.sijibomiaol.order_service.entity.OrderItem;
import com.sijibomiaol.order_service.entity.OrderProducts;
import com.sijibomiaol.order_service.repository.OrderProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    private final RestTemplate restTemplate;

    public OrderProductsServiceImpl() {
        this.restTemplate = new RestTemplate();
    }




    public ApiResponse getProductId(UUID productId) {
        try{
            String url = String.format("http://localhost:8081/api/v1/product/%s", productId);
            return restTemplate.getForObject(url,  ApiResponse.class, productId);

        }
        catch (Exception e){
            System.err.println("Error retrieving product" + e.getMessage());
            return null;
        }
    }




    private BigDecimal calculateTotalAmount(OrderRequest orderRequest) {
            List<OrderItemDto> orderItems = orderRequest.getOrderItems();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItemDto orderItemDto : orderItems) {
                ApiResponse apiResponse = null;
                try {
                    apiResponse = getProductId(orderItemDto.getProductId());
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }

                if (apiResponse != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ProductResponse productResponse = objectMapper.convertValue(apiResponse.getData(), ProductResponse.class);
                    BigDecimal itemTotal = productResponse.getPrice().multiply(new BigDecimal(orderItemDto.getQuantity()));
                    totalAmount = totalAmount.add(itemTotal);
                } else {
                    System.err.println("Error retrieving product" + orderItemDto.getProductId());
                }
            }
            return totalAmount;
        }

    @Override
    @Transactional
    public OrderProducts createOrderProduct(OrderRequest orderRequest){
        OrderProducts orderProducts = new OrderProducts();
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(dto ->{
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(dto.getQuantity());
                    orderItem.setProductId(dto.getProductId());
                    return orderItem;
                }).collect(Collectors.toList());


        orderProducts.setOrderItemList(orderItems);
        try {
            BigDecimal totalAmount = calculateTotalAmount(orderRequest);
            orderProducts.setTotalAmount(totalAmount);
        }catch (Exception e){
            log.error("Failed to calculate total amount for order request: {}", orderRequest, e);
            throw new RuntimeException("Failed to calculate total amount for order request: " + orderRequest, e);
        }

        try {
            return orderProductRepository.save(orderProducts);
        }
        catch (Exception e){
            log.error("Failed to save order product: {}", orderProducts, e);
        }


        return orderProductRepository.save(orderProducts);
    }

    @Override
    public OrderProducts getOrderProduct(UUID orderId) throws  Exception{
        Optional<OrderProducts> optionalOrder = orderProductRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        else {
            throw new Exception("Order id " + orderId + " not found");
        }
    }



    @Transactional
    @Override
    public OrderProducts cancelOrderProduct(UUID orderId) throws Exception{
        Optional<OrderProducts> optionalOrder = orderProductRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            OrderProducts orderProducts = optionalOrder.get();
            orderProducts.setOrderStatus(OrderStatus.CANCELLED);
            return orderProductRepository.save(orderProducts);
        }
        else {
            throw new Exception("Order not Found");
        }
    }




//
//    public Flux<OrderProducts> getOrderProducts(Long userId){
//        return Flux.fromIterable(orderProductRepository.findByUserId(userId))
//    }


}
