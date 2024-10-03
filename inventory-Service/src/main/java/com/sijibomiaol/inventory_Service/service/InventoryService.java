package com.sijibomiaol.inventory_Service.service;

import com.sijibomiaol.inventory_Service.dto.InventoryRequest;
import com.sijibomiaol.inventory_Service.dto.InventoryResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    @Transactional
    void addStock(InventoryRequest inventoryRequest);

    @Transactional
    void  reducedStock(InventoryRequest inventoryRequest);

    InventoryResponse getStockByProductId(UUID productId);

    List<InventoryResponse> getAllStock();

    @Transactional
    InventoryResponse addNewStock(InventoryRequest inventoryRequest);
}
