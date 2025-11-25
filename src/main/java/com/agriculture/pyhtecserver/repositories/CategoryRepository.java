package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.active = true")
    List<Category> findAllActiveCategories();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.displayOrder")
    List<Category> findAllRootCategories();

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId ORDER BY c.displayOrder")
    List<Category> findSubcategories(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Category> searchCategories(@Param("keyword") String keyword);

    @Query("SELECT c FROM Category c ORDER BY c.displayOrder")
    List<Category> findAllOrderedByDisplay();
}
