package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByEmail(String email);

    List<Supplier> findBySupplierType(Supplier.SupplierType supplierType);

    @Query("SELECT s FROM Supplier s WHERE s.active = true")
    List<Supplier> findAllActiveSuppliers();

    @Query("SELECT s FROM Supplier s WHERE s.rating >= :minRating ORDER BY s.rating DESC")
    List<Supplier> findByMinimumRating(@Param("minRating") double minRating);

    @Query("SELECT s FROM Supplier s WHERE s.city = :city")
    List<Supplier> findByCity(@Param("city") String city);

    @Query("SELECT s FROM Supplier s WHERE s.companyName LIKE %:name%")
    List<Supplier> searchByCompanyName(@Param("name") String name);
}
