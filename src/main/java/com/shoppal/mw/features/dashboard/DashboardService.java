package com.shoppal.mw.features.dashboard;

import com.shoppal.mw.features.products.Product;
import com.shoppal.mw.features.products.ProductService;
import com.shoppal.mw.features.sales.Sale;
import com.shoppal.mw.features.sales.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ProductService productService;
    private final SaleService saleService;

    public StatsResponse getStats() {
        List<Product> products = productService.getAllProducts();
        List<Sale> sales = saleService.getAllSales();

        BigDecimal totalRevenue = sales.stream()
                .map(Sale::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long lowStockCount = products.stream()
                .filter(p -> p.getStockQuantity() < 10)
                .count();

        return StatsResponse.builder()
                .totalRevenue(totalRevenue)
                .totalSales((long) sales.size())
                .totalProducts((long) products.size())
                .lowStockItems(lowStockCount)
                .build();
    }
}
