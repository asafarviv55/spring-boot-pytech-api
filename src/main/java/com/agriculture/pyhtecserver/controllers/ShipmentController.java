package com.agriculture.pyhtecserver.controllers;

import com.agriculture.pyhtecserver.models.Shipment;
import com.agriculture.pyhtecserver.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        log.info("Getting all shipments");
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        log.info("Getting shipment by id: {}", id);
        return shipmentService.getShipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        log.info("Creating new shipment for order: {}", shipment.getOrder().getId());
        Shipment created = shipmentService.createShipment(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(@PathVariable Long id, @RequestBody Shipment shipment) {
        log.info("Updating shipment with id: {}", id);
        try {
            Shipment updated = shipmentService.updateShipment(id, shipment);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        log.info("Deleting shipment with id: {}", id);
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        log.info("Getting shipment by tracking number: {}", trackingNumber);
        return shipmentService.getShipmentByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Shipment>> getShipmentsByOrder(@PathVariable Long orderId) {
        log.info("Getting shipments for order: {}", orderId);
        return ResponseEntity.ok(shipmentService.getShipmentsByOrder(orderId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Shipment>> getShipmentsByStatus(@PathVariable Shipment.ShipmentStatus status) {
        log.info("Getting shipments by status: {}", status);
        return ResponseEntity.ok(shipmentService.getShipmentsByStatus(status));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Shipment>> getShipmentsByWarehouse(@PathVariable Long warehouseId) {
        log.info("Getting shipments for warehouse: {}", warehouseId);
        return ResponseEntity.ok(shipmentService.getShipmentsByWarehouse(warehouseId));
    }

    @GetMapping("/carrier/{carrier}")
    public ResponseEntity<List<Shipment>> getShipmentsByCarrier(@PathVariable String carrier) {
        log.info("Getting shipments by carrier: {}", carrier);
        return ResponseEntity.ok(shipmentService.getShipmentsByCarrier(carrier));
    }

    @GetMapping("/delayed")
    public ResponseEntity<List<Shipment>> getDelayedShipments() {
        log.info("Getting delayed shipments");
        return ResponseEntity.ok(shipmentService.getDelayedShipments());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(@PathVariable Long id, @RequestParam Shipment.ShipmentStatus status) {
        log.info("Updating shipment {} status to {}", id, status);
        try {
            Shipment updated = shipmentService.updateShipmentStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Shipment>> getShipmentsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Getting shipments between {} and {}", startDate, endDate);
        return ResponseEntity.ok(shipmentService.getShipmentsBetweenDates(startDate, endDate));
    }
}
