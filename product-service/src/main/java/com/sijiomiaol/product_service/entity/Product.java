package com.sijiomiaol.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private String name;
    private String description;
    @Min(value=0, message = "Price can't be less 0 ")
    private BigDecimal price;


}
