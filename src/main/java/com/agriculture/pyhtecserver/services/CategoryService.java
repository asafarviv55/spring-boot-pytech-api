package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Category;
import com.agriculture.pyhtecserver.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setParent(categoryDetails.getParent());
        category.setActive(categoryDetails.isActive());
        category.setDisplayOrder(categoryDetails.getDisplayOrder());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findAllActiveCategories();
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findAllRootCategories();
    }

    public List<Category> getSubcategories(Long parentId) {
        return categoryRepository.findSubcategories(parentId);
    }

    public List<Category> searchCategories(String keyword) {
        return categoryRepository.searchCategories(keyword);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> getAllCategoriesOrdered() {
        return categoryRepository.findAllOrderedByDisplay();
    }
}
