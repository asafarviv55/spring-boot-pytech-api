package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Supplier;
import com.agriculture.pyhtecserver.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        supplier.setCompanyName(supplierDetails.getCompanyName());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setPhone(supplierDetails.getPhone());
        supplier.setAddress(supplierDetails.getAddress());
        supplier.setCity(supplierDetails.getCity());
        supplier.setCountry(supplierDetails.getCountry());
        supplier.setSupplierType(supplierDetails.getSupplierType());
        supplier.setRating(supplierDetails.getRating());
        supplier.setActive(supplierDetails.isActive());

        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findAllActiveSuppliers();
    }

    public List<Supplier> getSuppliersByType(Supplier.SupplierType type) {
        return supplierRepository.findBySupplierType(type);
    }

    public List<Supplier> getSuppliersByMinimumRating(double minRating) {
        return supplierRepository.findByMinimumRating(minRating);
    }

    public List<Supplier> searchSuppliersByName(String name) {
        return supplierRepository.searchByCompanyName(name);
    }

    public Optional<Supplier> getSupplierByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }
}
