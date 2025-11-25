package com.agriculture.pyhtecserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "current_stock_level")
    private int currentStockLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "warehouse_type")
    private WarehouseType warehouseType;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "active")
    private boolean active;

    @PrePersist
    protected void onCreate() {
        active = true;
        if (currentStockLevel == 0) {
            currentStockLevel = 0;
        }
    }

    public enum WarehouseType {
        MAIN,
        REGIONAL,
        DISTRIBUTION_CENTER,
        COLD_STORAGE,
        DRY_STORAGE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(id, warehouse.id) && Objects.equals(code, warehouse.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", warehouseType=" + warehouseType +
                ", capacity=" + capacity +
                ", currentStockLevel=" + currentStockLevel +
                '}';
    }
}
