package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Shipment;
import com.agriculture.pyhtecserver.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    public Shipment createShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipment(Long id, Shipment shipmentDetails) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));

        shipment.setOrder(shipmentDetails.getOrder());
        shipment.setTrackingNumber(shipmentDetails.getTrackingNumber());
        shipment.setWarehouse(shipmentDetails.getWarehouse());
        shipment.setStatus(shipmentDetails.getStatus());
        shipment.setCarrier(shipmentDetails.getCarrier());
        shipment.setShippingMethod(shipmentDetails.getShippingMethod());
        shipment.setEstimatedDeliveryDate(shipmentDetails.getEstimatedDeliveryDate());
        shipment.setActualDeliveryDate(shipmentDetails.getActualDeliveryDate());
        shipment.setShippingCost(shipmentDetails.getShippingCost());
        shipment.setNotes(shipmentDetails.getNotes());

        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    public Optional<Shipment> getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber);
    }

    public List<Shipment> getShipmentsByOrder(Long orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }

    public List<Shipment> getShipmentsByStatus(Shipment.ShipmentStatus status) {
        return shipmentRepository.findByStatus(status);
    }

    public List<Shipment> getShipmentsByWarehouse(Long warehouseId) {
        return shipmentRepository.findByWarehouseId(warehouseId);
    }

    public List<Shipment> getShipmentsByCarrier(String carrier) {
        return shipmentRepository.findByCarrier(carrier);
    }

    public List<Shipment> getShipmentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return shipmentRepository.findShipmentsBetweenDates(startDate, endDate);
    }

    public List<Shipment> getDelayedShipments() {
        return shipmentRepository.findDelayedShipments(LocalDateTime.now());
    }

    public Shipment updateShipmentStatus(Long id, Shipment.ShipmentStatus status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        shipment.setStatus(status);
        if (status == Shipment.ShipmentStatus.DELIVERED) {
            shipment.setActualDeliveryDate(LocalDateTime.now());
        }
        return shipmentRepository.save(shipment);
    }
}
