package com.shoppal.mw.features.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/{productId}/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public Sale recordSale(@PathVariable Long productId, @RequestBody SaleRequest request) {
        return saleService.recordSale(productId, request.getQuantity());
    }
}

// Inline DTO for simplicity
class SaleRequest {
    private Integer quantity;
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
