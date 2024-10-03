package com.sijibomiaol.inventory_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryRequest {
    private UUID productId;
    private String productName;
    private Long quantity;
}
