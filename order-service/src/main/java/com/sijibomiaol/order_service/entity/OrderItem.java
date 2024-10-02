package com.sijibomiaol.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID productId;
    private int quantity;
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private OrderProducts orderProducts;


}
