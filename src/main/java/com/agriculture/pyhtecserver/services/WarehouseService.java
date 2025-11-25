package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Warehouse;
import com.agriculture.pyhtecserver.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(Long id) {
        return warehouseRepository.findById(id);
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Long id, Warehouse warehouseDetails) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));

        warehouse.setName(warehouseDetails.getName());
        warehouse.setCode(warehouseDetails.getCode());
        warehouse.setAddress(warehouseDetails.getAddress());
        warehouse.setCity(warehouseDetails.getCity());
        warehouse.setCountry(warehouseDetails.getCountry());
        warehouse.setCapacity(warehouseDetails.getCapacity());
        warehouse.setCurrentStockLevel(warehouseDetails.getCurrentStockLevel());
        warehouse.setWarehouseType(warehouseDetails.getWarehouseType());
        warehouse.setManagerName(warehouseDetails.getManagerName());
        warehouse.setContactPhone(warehouseDetails.getContactPhone());
        warehouse.setActive(warehouseDetails.isActive());

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public List<Warehouse> getActiveWarehouses() {
        return warehouseRepository.findAllActiveWarehouses();
    }

    public List<Warehouse> getWarehousesByType(Warehouse.WarehouseType type) {
        return warehouseRepository.findByWarehouseType(type);
    }

    public List<Warehouse> getWarehousesWithLowStock() {
        return warehouseRepository.findWarehousesWithLowStock();
    }

    public List<Warehouse> getWarehousesNearCapacity() {
        return warehouseRepository.findWarehousesNearCapacity();
    }

    public List<Warehouse> searchWarehousesByName(String name) {
        return warehouseRepository.searchByName(name);
    }

    public Optional<Warehouse> getWarehouseByCode(String code) {
        return warehouseRepository.findByCode(code);
    }
}
