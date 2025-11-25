package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Product;
import com.agriculture.pyhtecserver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setSku(productDetails.getSku());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        product.setSupplier(productDetails.getSupplier());
        product.setUnitPrice(productDetails.getUnitPrice());
        product.setQuantityInStock(productDetails.getQuantityInStock());
        product.setReorderLevel(productDetails.getReorderLevel());
        product.setUnitOfMeasure(productDetails.getUnitOfMeasure());
        product.setWeight(productDetails.getWeight());
        product.setExpiryDate(productDetails.getExpiryDate());
        product.setActive(productDetails.isActive());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getActiveProducts() {
        return productRepository.findAllActiveProducts();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsBySupplier(Long supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }

    public List<Product> getProductsBelowReorderLevel() {
        return productRepository.findProductsBelowReorderLevel();
    }

    public List<Product> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findProductsByPriceRange(minPrice, maxPrice);
    }

    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public List<Product> getAllProductsOrderByStock() {
        return productRepository.findAllOrderByStockAsc();
    }
}
