package com.sijibomiaol.inventory_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryResponse {
    private Long id;
    private UUID productId;
    private String productName;
    private Long quantity;

    public InventoryResponse(UUID productId, String productName, Long quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }
}
