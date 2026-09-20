package com.shoppal.mw.features.products;

import com.shoppal.mw.features.sales.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }

        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            String causeMessage = e.getMostSpecificCause().getMessage();
            throw new ProductNotCreatedException(
                    "Product could not be created: " + causeMessage, e);
        } catch (DataAccessException e) {
            throw new ProductNotCreatedException("Product could not be created", e);
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        saleRepository.deleteByProductId(id);
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}


