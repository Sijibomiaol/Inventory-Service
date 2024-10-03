package com.sijibomiaol.inventory_Service.service;

import com.sijibomiaol.inventory_Service.dto.InventoryRequest;
import com.sijibomiaol.inventory_Service.dto.InventoryResponse;
import com.sijibomiaol.inventory_Service.entity.Inventory;
import com.sijibomiaol.inventory_Service.exception.ProductNotAvailableException;
import com.sijibomiaol.inventory_Service.exception.ProductNotFoundException;
import com.sijibomiaol.inventory_Service.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    @Transactional
    @Override
    public void  addStock(InventoryRequest inventoryRequest) {
        UUID productId = inventoryRequest.getProductId();
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(productId);
        try {
            if (inventoryOpt.isPresent()) {

                Inventory inventory = inventoryOpt.get();
                Long updateQuantity = inventory.getQuantity() + inventoryRequest.getQuantity();
                inventory.setQuantity(updateQuantity);
                inventoryRepository.save(inventory);
                log.info("Inventory updated");
            }
            else
                throw new ProductNotFoundException("Product not found in inventory with ID:" + productId);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    @Transactional
    @Override
    public void  reducedStock(InventoryRequest inventoryRequest) {
        UUID productId = inventoryRequest.getProductId();
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(productId);
        try {
            if (inventoryOpt.isPresent()) {
                Inventory inventory = inventoryOpt.get();
                long UpdateQuantity = inventory.getQuantity() - inventoryRequest.getQuantity();
                    if (UpdateQuantity <=0){
                        throw  new ProductNotAvailableException("Product not available");
                    }
                inventory.setQuantity(UpdateQuantity);
                inventoryRepository.save(inventory);
                log.info("Inventory updated");
            }

            else {
                throw new ProductNotFoundException("Product not found in inventory with ID:" + productId);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public InventoryResponse getStockByProductId(UUID productId) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(productId);
        if (inventoryOpt.isPresent() && inventoryOpt!=null) {
            Inventory inventory = inventoryOpt.get();
            return new InventoryResponse(inventory.getProductId(), inventory.getProductName(), inventory.getQuantity());
        }
        else {
            throw new ProductNotFoundException("Product not found in inventory with ID:" + productId);
        }
    }

    @Override
    public List<InventoryResponse> getAllStock() {
        List<Inventory> inventories = inventoryRepository.findAll();
        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        for (Inventory inventory : inventories) {
            inventoryResponses.add(new InventoryResponse(inventory.getProductId(), inventory.getProductName(), inventory.getQuantity()));
        }
        return inventoryResponses;
    }

    @Override
    public InventoryResponse addNewStock(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setProductId(inventoryRequest.getProductId());
        inventory.setProductName(inventoryRequest.getProductName());
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventoryRepository.save(inventory);
        return new InventoryResponse(inventory.getProductId(), inventory.getProductName(), inventory.getQuantity());
    }
}
