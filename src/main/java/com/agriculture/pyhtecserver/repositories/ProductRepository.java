package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findBySupplierId(Long supplierId);

    @Query("SELECT p FROM Product p WHERE p.active = true")
    List<Product> findAllActiveProducts();

    @Query("SELECT p FROM Product p WHERE p.quantityInStock <= p.reorderLevel")
    List<Product> findProductsBelowReorderLevel();

    @Query("SELECT p FROM Product p WHERE p.quantityInStock = 0")
    List<Product> findOutOfStockProducts();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.unitPrice BETWEEN :minPrice AND :maxPrice")
    List<Product> findProductsByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    @Query("SELECT p FROM Product p ORDER BY p.quantityInStock ASC")
    List<Product> findAllOrderByStockAsc();
}
