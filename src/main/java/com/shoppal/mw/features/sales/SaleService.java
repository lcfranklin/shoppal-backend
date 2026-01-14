package com.shoppal.mw.features.sales;

import com.shoppal.mw.features.products.Product;
import com.shoppal.mw.features.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;

    @Transactional
    public Sale recordSale(Long productId, Integer quantity) {
        Product product = productService.getProductById(productId);
        
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        // Update product stock
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productService.addProduct(product); // save updated product
        
        Sale sale = Sale.builder()
                .product(product)
                .quantity(quantity)
                .totalPrice(product.getPrice().multiply(new BigDecimal(quantity)))
                .saleDate(LocalDateTime.now())
                .build();
        
        return saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
}
