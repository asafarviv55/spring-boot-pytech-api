package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByOrderId(Long orderId);

    List<Shipment> findByStatus(Shipment.ShipmentStatus status);

    List<Shipment> findByWarehouseId(Long warehouseId);

    @Query("SELECT s FROM Shipment s WHERE s.carrier = :carrier")
    List<Shipment> findByCarrier(@Param("carrier") String carrier);

    @Query("SELECT s FROM Shipment s WHERE s.shipmentDate BETWEEN :startDate AND :endDate")
    List<Shipment> findShipmentsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s FROM Shipment s WHERE s.estimatedDeliveryDate < :currentDate AND s.status NOT IN ('DELIVERED', 'FAILED', 'RETURNED')")
    List<Shipment> findDelayedShipments(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT s FROM Shipment s ORDER BY s.shipmentDate DESC")
    List<Shipment> findAllOrderedByDate();
}
