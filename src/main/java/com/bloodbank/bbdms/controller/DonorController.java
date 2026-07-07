package com.bloodbank.bbdms.controller;

import com.bloodbank.bbdms.model.Donor;
import com.bloodbank.bbdms.repository.DonorRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorRepository donorRepository;

    public DonorController(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    // Home page donor cards + Need Blood search (optional bloodGroup filter)
    @GetMapping
    public Page<Donor> listDonors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String bloodGroup) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (bloodGroup != null && !bloodGroup.isBlank()) {
            return donorRepository.findByBloodGroup(bloodGroup, pageable);
        }
        return donorRepository.findAll(pageable);
    }

    // "Become a Donor" public form submission
    @PostMapping
    public Donor becomeDonor(@Valid @RequestBody Donor donor) {
        donor.setId(null);
        return donorRepository.save(donor);
    }
}
