package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByCode(String code);

    List<Warehouse> findByWarehouseType(Warehouse.WarehouseType warehouseType);

    @Query("SELECT w FROM Warehouse w WHERE w.active = true")
    List<Warehouse> findAllActiveWarehouses();

    @Query("SELECT w FROM Warehouse w WHERE w.city = :city")
    List<Warehouse> findByCity(@Param("city") String city);

    @Query("SELECT w FROM Warehouse w WHERE w.currentStockLevel < w.capacity * 0.2")
    List<Warehouse> findWarehousesWithLowStock();

    @Query("SELECT w FROM Warehouse w WHERE w.currentStockLevel >= w.capacity * 0.9")
    List<Warehouse> findWarehousesNearCapacity();

    @Query("SELECT w FROM Warehouse w WHERE w.name LIKE %:name%")
    List<Warehouse> searchByName(@Param("name") String name);
}
