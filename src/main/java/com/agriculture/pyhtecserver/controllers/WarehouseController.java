package com.agriculture.pyhtecserver.controllers;

import com.agriculture.pyhtecserver.models.Warehouse;
import com.agriculture.pyhtecserver.services.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        log.info("Getting all warehouses");
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        log.info("Getting warehouse by id: {}", id);
        return warehouseService.getWarehouseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        log.info("Creating new warehouse: {}", warehouse.getName());
        Warehouse created = warehouseService.createWarehouse(warehouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        log.info("Updating warehouse with id: {}", id);
        try {
            Warehouse updated = warehouseService.updateWarehouse(id, warehouse);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        log.info("Deleting warehouse with id: {}", id);
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Warehouse>> getActiveWarehouses() {
        log.info("Getting all active warehouses");
        return ResponseEntity.ok(warehouseService.getActiveWarehouses());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Warehouse>> getWarehousesByType(@PathVariable Warehouse.WarehouseType type) {
        log.info("Getting warehouses by type: {}", type);
        return ResponseEntity.ok(warehouseService.getWarehousesByType(type));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Warehouse>> getWarehousesWithLowStock() {
        log.info("Getting warehouses with low stock");
        return ResponseEntity.ok(warehouseService.getWarehousesWithLowStock());
    }

    @GetMapping("/near-capacity")
    public ResponseEntity<List<Warehouse>> getWarehousesNearCapacity() {
        log.info("Getting warehouses near capacity");
        return ResponseEntity.ok(warehouseService.getWarehousesNearCapacity());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Warehouse>> searchWarehouses(@RequestParam String name) {
        log.info("Searching warehouses by name: {}", name);
        return ResponseEntity.ok(warehouseService.searchWarehousesByName(name));
    }
}
