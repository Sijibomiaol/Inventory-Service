package com.sijibomiaol.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductResponse {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
}
