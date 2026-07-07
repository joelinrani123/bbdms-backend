package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.repository.ContactQueryRepository;
import com.bloodbank.bbdms.repository.DonorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final DonorRepository donorRepository;
    private final ContactQueryRepository contactQueryRepository;

    public AdminDashboardController(DonorRepository donorRepository, ContactQueryRepository contactQueryRepository) {
        this.donorRepository = donorRepository;
        this.contactQueryRepository = contactQueryRepository;
    }

    @GetMapping
    public Map<String, Long> stats() {
        long totalDonors = donorRepository.count();
        long totalQueries = contactQueryRepository.count();
        long pendingQueries = contactQueryRepository.findAll().stream()
                .filter(q -> "Pending".equalsIgnoreCase(q.getStatus()))
                .count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("donorsAvailable", totalDonors);
        stats.put("userQueries", totalQueries);
        stats.put("pendingQueries", pendingQueries);
        return stats;
    }
}
