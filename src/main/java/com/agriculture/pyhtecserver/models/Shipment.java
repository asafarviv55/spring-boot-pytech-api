package com.agriculture.pyhtecserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "tracking_number", unique = true)
    private String trackingNumber;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShipmentStatus status;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipment_date")
    private LocalDateTime shipmentDate;

    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDateTime actualDeliveryDate;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "notes")
    private String notes;

    @PrePersist
    protected void onCreate() {
        shipmentDate = LocalDateTime.now();
        if (status == null) {
            status = ShipmentStatus.PENDING;
        }
    }

    public enum ShipmentStatus {
        PENDING,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED,
        FAILED,
        RETURNED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(id, shipment.id) && Objects.equals(trackingNumber, shipment.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackingNumber);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.getId() : null) +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", status=" + status +
                ", carrier='" + carrier + '\'' +
                '}';
    }
}
