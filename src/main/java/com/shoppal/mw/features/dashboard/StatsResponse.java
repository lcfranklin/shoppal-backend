package com.shoppal.mw.features.dashboard;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StatsResponse {
    private BigDecimal totalRevenue;
    private Long totalSales;
    private Long totalProducts;
    private Long lowStockItems;
}
