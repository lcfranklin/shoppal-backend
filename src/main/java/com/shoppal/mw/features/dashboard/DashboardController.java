package com.shoppal.mw.features.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    @PostMapping("/revenue/adjust")
    public ResponseEntity<Void> adjustRevenue(@RequestParam BigDecimal amount, @RequestParam String reason) {
        dashboardService.addRevenueAdjustment(amount, reason);
        return ResponseEntity.ok().build();
    }
}
