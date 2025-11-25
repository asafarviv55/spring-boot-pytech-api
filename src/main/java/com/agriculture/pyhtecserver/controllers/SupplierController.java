package com.agriculture.pyhtecserver.controllers;

import com.agriculture.pyhtecserver.models.Supplier;
import com.agriculture.pyhtecserver.services.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        log.info("Getting all suppliers");
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        log.info("Getting supplier by id: {}", id);
        return supplierService.getSupplierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        log.info("Creating new supplier: {}", supplier.getCompanyName());
        Supplier created = supplierService.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        log.info("Updating supplier with id: {}", id);
        try {
            Supplier updated = supplierService.updateSupplier(id, supplier);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        log.info("Deleting supplier with id: {}", id);
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Supplier>> getActiveSuppliers() {
        log.info("Getting all active suppliers");
        return ResponseEntity.ok(supplierService.getActiveSuppliers());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Supplier>> getSuppliersByType(@PathVariable Supplier.SupplierType type) {
        log.info("Getting suppliers by type: {}", type);
        return ResponseEntity.ok(supplierService.getSuppliersByType(type));
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Supplier>> getSuppliersByMinimumRating(@RequestParam double minRating) {
        log.info("Getting suppliers with minimum rating: {}", minRating);
        return ResponseEntity.ok(supplierService.getSuppliersByMinimumRating(minRating));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> searchSuppliers(@RequestParam String name) {
        log.info("Searching suppliers by name: {}", name);
        return ResponseEntity.ok(supplierService.searchSuppliersByName(name));
    }
}
