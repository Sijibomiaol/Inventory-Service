package com.sijibomiaol.inventory_Service.contoller;

import com.sijibomiaol.inventory_Service.dto.InventoryRequest;
import com.sijibomiaol.inventory_Service.dto.InventoryResponse;
import com.sijibomiaol.inventory_Service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;
    @PostMapping("/add")
    public ResponseEntity<Void> addStock (@RequestBody InventoryRequest inventoryRequest) {
        try {
            inventoryService.addStock(inventoryRequest);
            log.info("stock updated");
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
        catch (Exception e) {
            log.error("Error adding stock", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @PostMapping("/reduce")
    public ResponseEntity<Void> reduceStock (@RequestBody InventoryRequest inventoryRequest) {
        try {
            inventoryService.reducedStock(inventoryRequest);
            log.info("stock updated");
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
        catch (Exception e) {
            log.error("Error reduce stock", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getStockbyProductId(@PathVariable UUID productId) {
        try {
            InventoryResponse InventoryResponse = inventoryService.getStockByProductId(productId);
            return ResponseEntity.status(HttpStatus.OK).body(InventoryResponse);
        }
        catch (Exception e) {
            log.error("Error getting stock", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllStock() {
        List<InventoryResponse> InventoryResponse = inventoryService.getAllStock();
        return ResponseEntity.status(HttpStatus.OK).body(InventoryResponse);
    }
    @PostMapping("/new-stock")
    public ResponseEntity<InventoryResponse> addnewStock (@RequestBody InventoryRequest inventoryRequest) {
        InventoryResponse InventoryResponse = inventoryService.addNewStock(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(InventoryResponse);
    }

}
